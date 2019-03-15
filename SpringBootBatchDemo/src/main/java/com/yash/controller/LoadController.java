package com.yash.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/load")
public class LoadController {

	@Autowired
	private JobLauncher joblauncher;
	
	@Autowired
	private Job job;
	
	@GetMapping
	public BatchStatus load() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		
		Map<String, JobParameter> map = new HashMap<>();
		map.put("time", new JobParameter(System.currentTimeMillis()));
		
		JobParameters parameters = new JobParameters(map);
		JobExecution jobExecution = joblauncher.run(job, parameters);
		
		System.out.println("jobExecution : " +jobExecution.getStatus());
		
		while(jobExecution.isRunning()) {
			System.out.println("batch is running...");
		}
		
		return jobExecution.getStatus();
	}
}
