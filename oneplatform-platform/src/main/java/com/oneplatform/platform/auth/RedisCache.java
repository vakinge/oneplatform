/*
 * Copyright 2016-2018 www.jeesuite.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.oneplatform.platform.auth;

import com.jeesuite.cache.command.RedisBatchCommand;
import com.jeesuite.cache.command.RedisObject;
import com.jeesuite.cache.command.RedisString;

/**
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2018年11月8日
 */
public class RedisCache implements Cache {

	private String keyPrefix;
	
	
	public RedisCache(String keyPrefix) {
		this.keyPrefix = keyPrefix + ":";
	}
	
	private String buildKey(String key){
		return this.keyPrefix.concat(key);
	}

	@Override
	public void setString(String key, String value) {
		new RedisString(buildKey(key)).set(value);
	}

	@Override
	public String getString(String key) {
		return new RedisString(buildKey(key)).get();
	}

	@Override
	public void setObject(String key, Object value) {
		new RedisObject(buildKey(key)).set(value);
	}

	@Override
	public <T> T getObject(String key) {
		return new RedisObject(buildKey(key)).get();
	}

	@Override
	public void remove(String key) {
		new RedisObject(buildKey(key)).remove();
	}

	@Override
	public void removeAll() {
		RedisBatchCommand.removeByKeyPrefix(keyPrefix);
	}

	@Override
	public boolean exists(String key) {
		return new RedisObject(buildKey(key)).exists();
	}

}
