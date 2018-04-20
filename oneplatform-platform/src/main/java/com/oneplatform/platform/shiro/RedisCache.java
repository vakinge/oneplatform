package com.oneplatform.platform.shiro;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import com.jeesuite.cache.command.RedisBatchCommand;
import com.jeesuite.cache.command.RedisObject;
import com.jeesuite.cache.redis.JedisProviderFactory;
import com.jeesuite.common.serializer.SerializeUtils;
import com.jeesuite.mybatis.kit.CacheKeyUtils;

import redis.clients.util.SafeEncoder;

@SuppressWarnings("unchecked")
public class RedisCache<K, V> implements Cache<K, V> {

	private String keyPrefix;
	private String keyPrefixPattern;
	private String groupName = RedisCacheManager.SESSION_CACHE_GROUP;
	
	public RedisCache(String cacheName) {
		keyPrefix = cacheName+":";
		keyPrefixPattern = cacheName + ":*";
	}

	@Override
	public void clear() throws CacheException {
		try {			
			Set<String> keys = JedisProviderFactory.getMultiKeyCommands(groupName).keys(keyPrefixPattern);
			if(keys != null && keys.size() > 0){
				RedisBatchCommand.removeObjectsWithGroup(groupName,keys.toArray(new String[0]));
			}
		} finally {
			JedisProviderFactory.getJedisProvider(groupName).release();
		}
	}

	@Override
	public V get(K key) throws CacheException {
		String cacheKey = buildCacheKey(key);
		return new RedisObject(cacheKey,groupName).get();
	}

	@Override
	public Set<K> keys() {
		Set<K> convertedKeys = new HashSet<K>();
		try {			
			Set<byte[]> keys = JedisProviderFactory.getMultiKeyBinaryCommands(groupName).keys(SafeEncoder.encode(keyPrefixPattern));
			if(keys != null && keys.size()>0){
				for(byte[] key:keys){
					convertedKeys.add((K)SafeEncoder.encode(key));
				}
			}
		} finally {
			JedisProviderFactory.getJedisProvider(groupName).release();
		}
		return convertedKeys;
	}

	@Override
	public V put(K key, V value) throws CacheException {
		String cacheKey = buildCacheKey(key);
		new RedisObject(cacheKey,groupName).set(value);
		return value;
	}

	@Override
	public V remove(K key) throws CacheException {
		RedisObject cache = new RedisObject(buildCacheKey(key),groupName);
		V value = cache.get();
		if(value != null)cache.remove();
		return value;
	}

	@Override
	public int size() {
		try {			
			Set<byte[]> keys = JedisProviderFactory.getMultiKeyBinaryCommands(groupName).keys(SafeEncoder.encode(keyPrefixPattern));
			return keys == null ? 0 : keys.size();
		} finally {
			JedisProviderFactory.getJedisProvider(groupName).release();
		}
	}

	@Override
	public Collection<V> values() {
		List<V> values = new ArrayList<V>();
		try {
			Set<byte[]> keys = JedisProviderFactory.getMultiKeyBinaryCommands(groupName).keys(SafeEncoder.encode(keyPrefixPattern));
			if(keys != null && keys.size()>0){
				for(byte[] key:keys){
					byte[] valueBytes = JedisProviderFactory.getBinaryJedisCommands(groupName).get(key);
					values.add((V)SerializeUtils.deserialize(valueBytes));
				}
			}
		} finally {
			JedisProviderFactory.getJedisProvider(groupName).release();
		}
		
		return values;
	}
	
	private String buildCacheKey(K key){
		return keyPrefix + CacheKeyUtils.toString(key);
	}

}
