package com.oneplatform.platform.filter.internal;

import java.io.Closeable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jeesuite.cache.command.RedisNumber;


/**
 * 单个人访问频率控制器
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2017年10月26日
 */
public class PerFrequencyLimiter implements Closeable{

	private static Logger log = LoggerFactory.getLogger(PerFrequencyLimiter.class);
	
	private Map<String, List<Long>> accessDatas = new ConcurrentHashMap<>();
	
	private List<String> maybeFullIds = new CopyOnWriteArrayList<>();

	private ScheduledExecutorService cleanScheduledExecutor = Executors.newScheduledThreadPool(1);
	
	private static final String CACHEKEY_TEMPLATE = "reqLimit:%s-%s";
	
	/**
	 * 采样时间窗口
	 */
	private int timeWindowSeconds;
	private long timeWindowMillis;
	
	/**
	 * 在一个时间窗口限制数
	 */
	private int permits;
	
	private int putFullQueueSize;
	
	private int gloabalScanFlag = 0;
	
	private int cleanNullSize = 1000;
	
	private volatile boolean cleanNulling = false;
	
	public PerFrequencyLimiter() {
		this(10, 10);
	}
	
	/**
	 * @param timeWindowSeconds 采样时间窗口
	 * @param permits 在一个时间窗口限制数
	 */
	public PerFrequencyLimiter(final int timeWindowSeconds,int permits) {
		this.permits = permits;
		this.timeWindowSeconds = timeWindowSeconds;
		this.timeWindowMillis = timeWindowSeconds * 1000;
		this.putFullQueueSize = permits - 2;
		
		cleanScheduledExecutor.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				long currentTime = System.currentTimeMillis();
				
				List<Long> accessPoints = null;
				Iterator<String> idsIterator;
				if(gloabalScanFlag == 5){
					//清理空记录
					if(cleanNulling = accessDatas.size() > cleanNullSize){
						log.debug("cleanNulling...");
						Set<String> keys = accessDatas.keySet();
						for (String key : keys) {
							List<Long> points = accessDatas.get(key);
							if(points.isEmpty()){
								points = null;
								accessDatas.remove(key);
							}
						}
						cleanNulling = false;
					}
					
					idsIterator = accessDatas.keySet().iterator();
					gloabalScanFlag = 0;
				}else{
					idsIterator = maybeFullIds.iterator();
					gloabalScanFlag++;
				}
				
				while(idsIterator.hasNext()){
					accessPoints = accessDatas.get(idsIterator.next());
					//
					removeExpirePoints(accessPoints, currentTime);
				}
			}
			
		}, 1000, 1000, TimeUnit.MILLISECONDS);
	}

	
	public boolean tryAcquire(HttpServletRequest request,String identification){
		
		String uri = request.getRequestURI();
		boolean pass = requestFrequencyCheck(identification, uri);
		
		if(pass){
			//post接口
//			if(HttpMethod.POST.name().equals(request.getMethod())){
//				pass = postRequestCheck(uri, identification);		
//			}
		}
		
		if(!pass)log.info("request_frequency_limit identification:{} ,request uri:{},accessRecords:{}",identification,uri,accessDatas.get(identification));
		
		return pass;
	}

	private int removeExpirePoints(List<Long> ponits,long currentTimeMillis){
		int removeNums = 0;
		if(ponits == null || ponits.isEmpty()){
			return removeNums;
		}
		Iterator<Long> pointsIterator = ponits.iterator();  
		while (pointsIterator.hasNext()) {
			if(pointsIterator.next().compareTo(currentTimeMillis - timeWindowMillis) <= 0){
				pointsIterator.remove();
				removeNums++;
			}else{
				break;
			}
		}  
		
		return removeNums;
	}
	
	/**
	 * 访问频率检查
	 * @param identification 用户标识(ip or sessionId)
	 * @param uri
	 * @return
	 */
	private boolean requestFrequencyCheck(String identification,String uri){
		
		while(cleanNulling);
		
		boolean result = false;
		long currentTime = System.currentTimeMillis();
		List<Long> accessPoints = accessDatas.get(identification);
		try {
			if(accessPoints == null){
				accessPoints = new Vector<>(permits);
				accessDatas.put(identification, accessPoints);
			}
			
			int size;
			if((size = accessPoints.size()) < permits){
				if(size >= putFullQueueSize){
					maybeFullIds.add(identification);
				}
				result = true;
			}else{				
				int removeNums = removeExpirePoints(accessPoints, currentTime);
				result = removeNums > 0;
			}
			return result;
		} finally {
			accessPoints.add(currentTime);
		}
	}
	
	/**
	 * post接口检查
	 * @param uri
	 * @param identification
	 * @return
	 */
    private boolean postRequestCheck(String uri,String identification){
		
		String key = String.format(CACHEKEY_TEMPLATE, identification,uri);
		RedisNumber redisNumber = new RedisNumber(key);
		long count = redisNumber.increase(1);
		if(count == 1){			
			redisNumber.setExpire(timeWindowSeconds);
		}
		return count < permits;
	}
	

	public void close() {
		cleanScheduledExecutor.shutdown();
	}
	
	public static void main(String[] args) {
		
		int mockNum = 5;
		ExecutorService executorService = Executors.newFixedThreadPool(mockNum);
		PerFrequencyLimiter limiter = new PerFrequencyLimiter();
		
		for (int i = 0; i < 5000; i++) {
			String id = "id-"+(i % mockNum);
			System.out.println("id:" + id + ",tryAcquire:" + limiter.requestFrequencyCheck(id,"test"));
			try {Thread.sleep(RandomUtils.nextLong(10, 400));} catch (Exception e) {}
			
		}
		
		limiter.close();
		executorService.shutdown();
	}

}
