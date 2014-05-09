package org.horiga.study.service;

import javax.annotation.PostConstruct;

import org.horiga.study.ApplicationSettings;
import org.horiga.study.ConfigurationSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class StudyService {
	
	private static Logger log = LoggerFactory.getLogger(StudyService.class);
	
	@Autowired
	private ApplicationSettings applicationSettings;
	
	@Autowired
	private ConfigurationSettings configurations;
	
	@Async
	public void execute() {
		log.info("- study.execute, configuration.domain={}", configurations.getDomain());
	}
	
	@PostConstruct
	public void construct() {
		log.info(">> settings.configurations={}", this.configurations.toString());
		log.info(">> settings.application={}", this.configurations.toString());
	}
	
}
