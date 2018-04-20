package com.oneplatform.platform.shiro;

import java.io.Serializable;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;

import com.jeesuite.cache.command.RedisString;
import com.jeesuite.common.json.JsonUtils;
import com.oneplatform.base.GlobalContants;

public class RedisSessionDAO extends CachingSessionDAO {

	private String keyPrefix = "shiro.session:";
	
	@Override
	protected void doUpdate(Session session) {
		if(session == null || session.getId() == null){
			return;
		}
		String key = keyPrefix + session.getId();
		new RedisString(key,RedisCacheManager.SESSION_CACHE_GROUP).set(JsonUtils.toJson(session), GlobalContants.SESSION_EXPIRE_SECONDS);
	}


	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = this.generateSessionId(session);  
        this.assignSessionId(session, sessionId);
        this.doUpdate(session);
		return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		if(sessionId == null){
			return null;
		}
		String key = keyPrefix + sessionId;
		String sessionJson = new RedisString(key,RedisCacheManager.SESSION_CACHE_GROUP).get();
		if(sessionJson != null){
			SimpleSession session = JsonUtils.toObject(sessionJson, SimpleSession.class);
			session.setId(sessionId);
			return session;
		}
		return null;
	}

	@Override
	protected void doDelete(Session session) {

		if(session == null || session.getId() == null){
			return;
		}
		String key = keyPrefix + session.getId();
		new RedisString(key,RedisCacheManager.SESSION_CACHE_GROUP).remove();
	}
	
}

