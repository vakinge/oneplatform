/**
 * 
 */
package com.oneplatform.user.scheduler;

import java.util.Date;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Component;

import com.jeesuite.common.util.DateUtils;
import com.jeesuite.scheduler.AbstractJob;
import com.jeesuite.scheduler.JobContext;
import com.jeesuite.scheduler.annotation.ScheduleConf;

/**
 * 
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2017年11月7日
 */
@Component
@ScheduleConf(cronExpr="0 */5 * * * ?",jobName="demoTask",executeOnStarted = true)
public class DemoTask extends AbstractJob{

	@Override
	public void doJob(JobContext context) throws Exception {
		System.out.println("\n=============\nDemoTask_1=====>"+context.getNodeId()+"--"+DateUtils.format(new Date())+"\n===============\n");
		Thread.sleep(RandomUtils.nextLong(1000, 2000));
	}

	@Override
	public boolean parallelEnabled() {
		return false;
	}

}
