package org.horiga.study;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("jdbc")
public class JdbcConnectionSettings {
	
	private String driver;
	private String url;
	private String username;
	private String password;
	
}
