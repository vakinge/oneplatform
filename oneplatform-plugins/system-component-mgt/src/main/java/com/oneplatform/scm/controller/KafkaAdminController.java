package com.oneplatform.scm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeesuite.common.model.SelectOption;
import com.jeesuite.common.util.ResourceUtils;
import com.jeesuite.kafka.monitor.KafkaMonitor;
import com.jeesuite.kafka.monitor.model.BrokerInfo;
import com.jeesuite.kafka.monitor.model.ConsumerGroupInfo;
import com.jeesuite.kafka.monitor.model.ProducerStat;
import com.jeesuite.springweb.model.WrapperResponse;
import com.jeesuite.springweb.model.WrapperResponseEntity;


/**
 * 
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2018年4月10日
 */
@Controller
@RequestMapping("/scm/kafka")
public class KafkaAdminController implements InitializingBean {
	
private static KafkaMonitor monitor;
	
	@RequestMapping(value = "brokers", method = RequestMethod.GET)
	public @ResponseBody WrapperResponse<List<BrokerInfo>> getBrokerInfos(){
		List<BrokerInfo> data = null;
		if(monitor != null)data = monitor.getAllBrokers();
		return new WrapperResponse<>(data);
	}
	
	@RequestMapping(value = "groups", method = RequestMethod.GET)
	public @ResponseBody WrapperResponse<List<SelectOption>> getGroups(){
		
		List<SelectOption> result = new ArrayList<>();
		if(monitor != null){			
			List<ConsumerGroupInfo> groupInfos = monitor.getAllConsumerGroupInfos();
			for (ConsumerGroupInfo g : groupInfos) {
				if(g == null)continue;
				result.add(new SelectOption(g.getGroupName(),g.getGroupName()));
			}
			
			Set<String> groups = monitor.getAllProducerStats().keySet();
			for (String g : groups) {
				SelectOption opt = new SelectOption(g,g);
				if(result.contains(opt))continue;
				result.add(opt);
			}
		}
		return new WrapperResponse<>(result);
	}

	@RequestMapping(value = "topicinfo/{group}", method = RequestMethod.GET)
	public @ResponseBody WrapperResponseEntity topicinfos(@PathVariable("group") String group){

		Map<String,Object> result = new HashMap<>();
		List<ConsumerGroupInfo> groupInfos = monitor.getAllConsumerGroupInfos();
		for (ConsumerGroupInfo consumerGroup : groupInfos) {
			if(consumerGroup.getGroupName().equals(group)){
				result.put("consumer", consumerGroup.getTopics());
				break;
			}
		}
		
		List<ProducerStat> producerStats = monitor.getProducerStats(group);
		if(producerStats != null && producerStats.size() > 0){
			result.put("producer", producerStats);
		}
		
		return new WrapperResponseEntity(result);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if(ResourceUtils.containsProperty("zookeeper.servers") && ResourceUtils.containsProperty("kafka.bootstrap.servers")){
			 monitor = new KafkaMonitor(ResourceUtils.getProperty("zookeeper.servers"), ResourceUtils.getProperty("kafka.bootstrap.servers"), 1000);
		}
	}
}
