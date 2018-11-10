package com.oneplatform.scm.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeesuite.common.model.SelectOption;
import com.jeesuite.common.util.ResourceUtils;
import com.jeesuite.scheduler.model.JobGroupInfo;
import com.jeesuite.scheduler.monitor.MonitorCommond;
import com.jeesuite.scheduler.monitor.SchedulerMonitor;
import com.jeesuite.springweb.model.WrapperResponse;


/**
 * 
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2018年4月10日
 */
@RequestMapping("/scm/schedule")
@Controller
public class ScheduleAdminController implements InitializingBean{

	private SchedulerMonitor monitor;

	@RequestMapping(value = "groups", method = RequestMethod.GET)
	public @ResponseBody WrapperResponse<List<SelectOption>> getGroups(){
		
		List<SelectOption> result = new ArrayList<>();
		if(monitor != null){			
			List<String> groups = null;
			
			try {
				groups = monitor.getGroups();
			} catch (Exception e) {
				// TODO: may be no node
			}
			if(groups != null){
				for (String g : groups) {
					result.add(new SelectOption(g,g));
				}
			}
		}
		return new WrapperResponse<>(result);
	}
	
	@RequestMapping(value = "clean_invalid_group", method = RequestMethod.GET)
	public @ResponseBody WrapperResponse<String> clearInvalidGroup(){
		if(monitor != null )monitor.clearInvalidGroup();
		return new WrapperResponse<>();
	}
	
	
	@RequestMapping(value = "jobs", method = RequestMethod.GET)
	public @ResponseBody WrapperResponse<JobGroupInfo> listGroupAllJobs(@RequestParam(value="group") String group){
		if(StringUtils.isBlank(group)){			
			List<String> groups = monitor.getGroups();
			if(!groups.isEmpty()){
				group = groups.get(0);
			}
		}
		JobGroupInfo groupInfo = null;
		if(StringUtils.isNotBlank(group)){
			groupInfo = monitor.getJobGroupInfo(group);
		}
		return new WrapperResponse<>(groupInfo);
	}
	
	@RequestMapping(value = "job/{cmd}", method = RequestMethod.POST)
	public @ResponseBody WrapperResponse<String> sendCommond(@PathVariable("cmd") String cmd,@RequestBody Map<String, String> params){
		
		String group = params.get("group");
		String job = params.get("job");
		String data = params.get("data");
		
		MonitorCommond monitorCmd = null;
		if("exec".equals(cmd)){
			monitorCmd = new MonitorCommond(MonitorCommond.TYPE_EXEC, group, job, null);
		}else if("switch".equals(cmd)){
			monitorCmd = new MonitorCommond(MonitorCommond.TYPE_STATUS_MOD, group, job, data);
		}else if("updatecron".equals(cmd)){
			monitorCmd = new MonitorCommond(MonitorCommond.TYPE_CRON_MOD, group, job, data);
		}
		
		WrapperResponse<String> rspEntity = null;
		if(monitorCmd != null){
			try {	
				monitor.publishEvent(monitorCmd);
			    rspEntity = new WrapperResponse<String>(200, "发送执行事件成功");
			} catch (Exception e) {
				rspEntity = new WrapperResponse<String>(500, "publish Event发生错误");
			}
		}else{
			rspEntity = new WrapperResponse<String>(500, "未知指令："+cmd);
		}
		
		return rspEntity;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if(ResourceUtils.containsProperty("zookeeper.servers")){
			monitor = new SchedulerMonitor("zookeeper", ResourceUtils.getProperty("zookeeper.servers"));
		}
	}
}
