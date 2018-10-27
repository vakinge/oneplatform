package com.oneplatform.base.kit;

import java.io.Serializable;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.common.eventbus.AsyncEventBus;
import com.jeesuite.kafka.handler.MessageHandler;
import com.jeesuite.kafka.message.DefaultMessage;
import com.jeesuite.kafka.spring.TopicProducerSpringProvider;
import com.jeesuite.kafka.thread.StandardThreadExecutor.StandardThreadFactory;
import com.jeesuite.spring.InstanceFactory;

/**
 * 
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2018年1月20日
 */
public class AsyncTaskProduceClient {
	
	private static AsyncTaskProduceClient instance = new AsyncTaskProduceClient();

	private ExecutorService executorService;
	private AsyncEventBus asyncEventBus;
	
	private TopicProducerSpringProvider topicProducer;
	
	private AsyncTaskProduceClient() {
		try {			
			topicProducer = InstanceFactory.getInstance(TopicProducerSpringProvider.class);
		} catch (Exception e) {}
		
		if(topicProducer == null){
			executorService = Executors.newFixedThreadPool(2,new StandardThreadFactory("async-event-executor"));
			asyncEventBus = new AsyncEventBus(executorService);
			Collection<MessageHandler> handlers = InstanceFactory.getInstanceProvider().getInterfaces(MessageHandler.class).values();
			for (MessageHandler messageHandler : handlers) {
				asyncEventBus.register(messageHandler);
			}
		}
	}
	
	/**
	 * @param topic
	 * @param message
	 */
	public static void send(String topic,Serializable message){
		if(instance.topicProducer == null){
			instance.asyncEventBus.post(new DefaultMessage(message));
			return;
		}
		instance.topicProducer.publish(topic,new DefaultMessage(message));
	}
	
	public void close(){
		if(executorService != null){
			executorService.shutdown();
			executorService = null;
		}
	}
}
