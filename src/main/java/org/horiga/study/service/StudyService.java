package org.horiga.study.service;

import java.util.UUID;
import java.util.concurrent.Future;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.horiga.study.configuration.ApplicationSettings;
import org.horiga.study.configuration.ConfigurationSettings;
import org.horiga.study.domain.Study;
import org.horiga.study.repository.StudyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

@Service
public class StudyService {
	
	private static Logger log = LoggerFactory.getLogger(StudyService.class);
	
	@Autowired
	private ApplicationSettings applicationSettings;
	
	@Autowired
	private ConfigurationSettings configurations;
	
	@Autowired
	private StudyMapper studyMapper;
	
	@Async
	public Future<Void> execute() {
		log.info("- study.execute, configuration.domain={}", configurations.getDomain());
		return new AsyncResult<Void>(null);
	}
	
	@Async
	public Future<Study> findOne( String id) {
		return new AsyncResult<Study>(studyMapper.findById(id));
	}
	
	@Async
	public Future<Void> addUser(String name) {
		studyMapper.insert("s" + UUID.randomUUID().toString().replaceAll("-", ""), StringUtils.defaultString(name, "study.tarou"));
		return new AsyncResult<Void>(null);
	}
	
	@PostConstruct
	public void construct() {
		log.info(">> settings.configurations={}", this.configurations.toString());
		log.info(">> settings.application={}", this.configurations.toString());
	}
}
