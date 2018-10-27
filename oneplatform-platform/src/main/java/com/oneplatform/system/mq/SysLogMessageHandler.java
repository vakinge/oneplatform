/**
 * 
 */
package com.oneplatform.system.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.eventbus.Subscribe;
import com.jeesuite.kafka.annotation.ConsumerHandler;
import com.jeesuite.kafka.handler.MessageHandler;
import com.jeesuite.kafka.message.DefaultMessage;
import com.oneplatform.base.log.LogContext;
import com.oneplatform.base.log.LogContext.LogObject;
import com.oneplatform.system.dao.entity.SysLogEntity;
import com.oneplatform.system.dao.mapper.SysLogEntityMapper;

/**
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2016年6月25日
 */
@Component
@ConsumerHandler(topic = LogContext.SYS_LOG_TOPIC)
public class SysLogMessageHandler implements MessageHandler {

	@Autowired
	private SysLogEntityMapper sysLogMapper;

	@Override
	public void p1Process(DefaultMessage message) {}

	@Override
	@Subscribe
	public void p2Process(DefaultMessage message) {
		LogObject body = (LogObject) message.getBody();
		SysLogEntity logEntity = new SysLogEntity(body);
		sysLogMapper.insertSelective(logEntity);
	}


	@Override
	public boolean onProcessError(DefaultMessage message) {
		return false;
	}

}
