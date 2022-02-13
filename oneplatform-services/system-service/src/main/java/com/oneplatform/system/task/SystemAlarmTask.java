package com.oneplatform.system.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeesuite.scheduler.AbstractJob;
import com.jeesuite.scheduler.JobContext;
import com.jeesuite.scheduler.annotation.ScheduleConf;
import com.oneplatform.system.dao.mapper.ActionLogEntityMapper;

@Service
@ScheduleConf(jobName = "systemAlarmTask",cronExpr = "0/10 * * * * ?")
public class SystemAlarmTask extends AbstractJob {

	@Autowired
	private ActionLogEntityMapper actionLogMapper;
	
	@Override
	public boolean parallelEnabled() {
		return false;
	}
	

	@Override
	public boolean logging() {
		return false;
	}



	@Override
	public void doJob(JobContext context) throws Exception {
		System.out.println("===SystemAlarmTask===");
	}

}
