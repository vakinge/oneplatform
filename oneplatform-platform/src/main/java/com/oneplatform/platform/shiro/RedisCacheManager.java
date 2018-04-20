package com.oneplatform.platform.shiro;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

public class RedisCacheManager implements CacheManager {

	public static final String SESSION_CACHE_GROUP = null;
	
	private final Map<String, Cache<?, ?>> caches = new ConcurrentHashMap<>();
	@Override
	@SuppressWarnings("unchecked")
	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		
		Cache<K, V> cache = (Cache<K, V>) caches.get(name);
		
		if(cache == null){
			cache = new RedisCache<>(name);
			caches.put(name, cache);
		}
		return cache;
	}

}
