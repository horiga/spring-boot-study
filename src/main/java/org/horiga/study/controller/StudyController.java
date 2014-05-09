package org.horiga.study.controller;

import org.horiga.study.domain.Response;
import org.horiga.study.service.StudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/study")
public class StudyController {
	
	@Autowired
	private StudyService studyService;
	
	@RequestMapping(value={"/", "/index"}, method={RequestMethod.GET})
	@ResponseBody
	public Response index() {
		
		studyService.execute();
		
		return Response.success();
	}
	
}
