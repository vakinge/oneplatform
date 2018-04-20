package com.oneplatform.base.kit;

import java.io.Serializable;

import com.google.common.eventbus.AsyncEventBus;
import com.jeesuite.kafka.message.DefaultMessage;
import com.jeesuite.kafka.spring.TopicProducerSpringProvider;
import com.jeesuite.spring.InstanceFactory;

/**
 * 
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2018年1月20日
 */
public class KafkaProduceClient {
	
	private static KafkaProduceClient instance = new KafkaProduceClient();

	private AsyncEventBus asyncEventBus;
	
	private TopicProducerSpringProvider topicProducer;
	
	private KafkaProduceClient() {
		try {			
			topicProducer = InstanceFactory.getInstance(TopicProducerSpringProvider.class);
		} catch (Exception e) {}
		
		if(topicProducer == null){
			//TODO 初始化
		}
	}
	
	/**
	 * @param topic
	 * @param message
	 */
	public static void send(String topic,Serializable message){
		if(instance.topicProducer == null)return;
		instance.topicProducer.publish(topic,new DefaultMessage(message));
	}
}
