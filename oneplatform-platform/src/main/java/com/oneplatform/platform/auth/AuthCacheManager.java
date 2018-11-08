package com.oneplatform.platform.auth;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jeesuite.cache.redis.JedisProvider;
import com.jeesuite.cache.redis.JedisProviderFactory;
import com.jeesuite.cache.redis.standalone.JedisStandaloneProvider;
import com.jeesuite.common.util.ResourceUtils;
import com.oneplatform.base.model.LoginSession;

import redis.clients.jedis.BinaryJedis;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;

@Component
public class AuthCacheManager implements InitializingBean{

	private static final String SESSION_CACHE_NAME = "sessionCache";
	private static final String RESOURCE_CACHE_NAME = "resourceCache";
	private static final String PERM_CACHE_NAME = "permCache";
	private static final String CACHE_GROUP = "_redisAuth";
	private Map<String, Cache> caches = new ConcurrentHashMap<String, Cache>();
	
	@Value("${auth.storage.type:memory}")
	private String cacheType;
	
	private void addCahe(String cacheName,int timeToLiveSeconds){
		Cache cache = null;
		if("redis".equals(cacheType)){
			cache = new RedisCache(cacheName);
		}else{
			cache = new MemoryCache(timeToLiveSeconds);
		}
		caches.put(cacheName, cache);
	}
	
	public Cache getSessionCache(){
		return caches.get(SESSION_CACHE_NAME);
	}
	
	public Cache getResourceCache(){
		return caches.get(RESOURCE_CACHE_NAME);
	}
	
	public Cache getPermCache(){
		return caches.get(PERM_CACHE_NAME);
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if("redis".equals(cacheType)){
			String server = ResourceUtils.getProperty("auth.redis.servers", ResourceUtils.getProperty("jeesuite.cache.servers"));
			String datebase = ResourceUtils.getProperty("auth.redis.datebase", ResourceUtils.getProperty("jeesuite.cache.datebase","0"));
			String password = ResourceUtils.getProperty("auth.redis.password", ResourceUtils.getProperty("jeesuite.cache.password"));
			String maxPoolSize = ResourceUtils.getProperty("auth.redis.maxPoolSize", ResourceUtils.getProperty("jeesuite.cache.maxPoolSize","100"));
			
			Validate.notBlank(server, "config[auth.redis.servers] not found");
			
			JedisPoolConfig poolConfig = new JedisPoolConfig();
			poolConfig.setMaxIdle(1);
			poolConfig.setMinEvictableIdleTimeMillis(30*60*1000);
			poolConfig.setMaxTotal(Integer.parseInt(maxPoolSize));
			poolConfig.setMaxWaitMillis(5 * 1000);
			
			String[] servers = server.split(";|,");
			JedisProvider<Jedis,BinaryJedis> provider = new JedisStandaloneProvider(CACHE_GROUP, poolConfig, servers, 30000, password, Integer.parseInt(datebase),null);
			JedisProviderFactory.addProvider(provider);
		}
		
		addCahe(SESSION_CACHE_NAME, LoginSession.SESSION_EXPIRE_SECONDS);
		addCahe(RESOURCE_CACHE_NAME, 3600 * 24);
		addCahe(PERM_CACHE_NAME,LoginSession.SESSION_EXPIRE_SECONDS + 1);
	}
	
}
