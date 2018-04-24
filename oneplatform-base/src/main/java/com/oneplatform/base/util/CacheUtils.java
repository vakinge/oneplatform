/**
 * 
 */
package com.oneplatform.base.util;

import java.io.Serializable;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jeesuite.cache.command.RedisObject;

/**
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2016年12月30日
 */
public class CacheUtils {
	
	private static Logger logger = LoggerFactory.getLogger(CacheUtils.class);

	//缓存时间 （秒）
	public static long expireTime = 1800;

		
	public static <T> void addCache(T bean,Serializable id){
		String key = buildCacheKey(bean.getClass(), id);
		new RedisObject(key).set(bean, expireTime);
	}
	
	/**
	 * 移除指定实体组所有缓存
	 * @param entityClass
	 */
	public static void removeCache(Class<?> entityClass,Serializable id){
		String key = buildCacheKey(entityClass, id);
		new RedisObject(key).remove();
	}

	
	private static String buildCacheKey(Class<?> entityClass,Serializable id){
		return entityClass.getSimpleName() + ".id:" + id;
	}
	
	public static <T> T queryCache(Class<?> entityClass,Serializable id){
		String key = buildCacheKey(entityClass, id);
		return new RedisObject(key).get();
	}
	
	public static <T> T queryTryCache(String key,Callable<T> dataCaller){
		return queryTryCache(key, dataCaller, expireTime);
	}
	
	public static <T> T queryTryCache(String key,Callable<T> dataCaller,long expire){
		RedisObject redisObject = new RedisObject(key);
		T result = redisObject.get();
		if(result == null){
			try {				
				result = dataCaller.call();
				if(result != null){
					redisObject.set(result, expire);
					logger.debug("add cache key:{}",key);
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}else{
			logger.debug("query from cache by key:{}",key);
		}
		return result;
	}
}
