package org.horiga.study.domain;

import java.util.Date;

import lombok.Data;

@Data
public class Study {
	private String id;
	private String name;
	private Date regdt;
	private Date modidt;
}
