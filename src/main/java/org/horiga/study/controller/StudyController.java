package org.horiga.study.controller;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.horiga.study.domain.Response;
import org.horiga.study.domain.Study;
import org.horiga.study.service.StudyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/study")
public class StudyController {
	
	private static Logger log = LoggerFactory.getLogger(StudyController.class);
	
	@Autowired
	private StudyService studyService;
	
	@RequestMapping(value={"/", "/index"}, method={RequestMethod.GET})
	@ResponseBody
	public Response index() {
		log.info("controller.study.index - start");
		studyService.execute();
		return Response.success();
	}
	
	@RequestMapping(value={"/user/{id}"}, method={RequestMethod.GET})
	@ResponseBody
	public Study findOne(@PathVariable("id") String id) throws Exception {
		Future<Study> res = studyService.findOne(id);
		return res.get(1000, TimeUnit.MILLISECONDS);
	}
	
	@RequestMapping(value={"/user"}, method={RequestMethod.POST})
	@ResponseBody
	public Response register(@RequestParam("name") String name) {
		studyService.addUser(name);
		return Response.success();
	}
	
	@ExceptionHandler(TimeoutException.class)
	@ResponseBody
	public Response handleTimeout() {
		Response to = new Response();
		to.setMessage("timeout");
		return to;
	}
	
}
