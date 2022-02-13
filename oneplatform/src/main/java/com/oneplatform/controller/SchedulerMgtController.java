package com.oneplatform.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jeesuite.common.GlobalRuntimeContext;
import com.jeesuite.common.annotation.ApiMetadata;
import com.jeesuite.common.constants.PermissionLevel;
import com.jeesuite.scheduler.JobContext;
import com.jeesuite.scheduler.model.JobConfig;
import com.jeesuite.scheduler.model.JobGroupInfo;
import com.jeesuite.springweb.client.GenericApiRequest;
import com.jeesuite.zuul.CurrentSystemHolder;
import com.jeesuite.zuul.model.BizSystemModule;

/**
 * 
 * 
 * <br>
 * Class Name   : SchedulerMgtController
 *
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @version 1.0.0
 * @date Feb 12, 2022
 */
@RestController
@RequestMapping("/schedule")
public class SchedulerMgtController {

	@GetMapping("jobs")
	@ApiMetadata(permissionLevel = PermissionLevel.LoginRequired,actionLog = false)
	public List<JobConfig> getJobList(@RequestParam(name = "serviceId",required = false) String serviceId){
		List<JobConfig> list = new ArrayList<>();
		List<BizSystemModule> modules = CurrentSystemHolder.getModules();
		for (BizSystemModule module : modules) {
			if(serviceId != null && !serviceId.equals(module.getServiceId())) {
				continue;
			}
			list.addAll(fetchModuleJobs(module));
		}
		return list;
	}

	private List<JobConfig> fetchModuleJobs(BizSystemModule module) {
		
		List<JobConfig> jobs;
		try {
			if(GlobalRuntimeContext.APPID.equals(module.getRouteName())) {
				jobs = JobContext.getContext().getRegistry().getAllJobs();
			}else {		
				String url;
				if(module.getServiceId().startsWith("http")) {
					url = String.format("%s/scheduler/status", module.getServiceId());
				}else {
					url = String.format("http://%s/scheduler/status", module.getServiceId());
				}
				JobGroupInfo jobGroup = new GenericApiRequest.Builder().requestUrl(url).responseClass(JobGroupInfo.class).backendInternalCall().build().waitResponse();
				jobs = jobGroup.getJobs();
			}
		} catch (Exception e) {
			jobs = new ArrayList<>(0);
		}
		
		return jobs;
	}
}
