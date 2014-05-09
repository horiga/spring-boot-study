package org.horiga.study.domain;

import lombok.Data;

@Data
public class Response {
	
	private static final Response SUCCESS = new Response();
	
	public static Response success() {
		return SUCCESS;
	}
	
	String message = "";
}
