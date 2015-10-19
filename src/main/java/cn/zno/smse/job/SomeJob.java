package cn.zno.smse.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SomeJob implements Job {

	private final Logger logger = LoggerFactory.getLogger(SomeJob.class);
	
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		
		logger.debug("--------------------do --------job----------");
	}

}
