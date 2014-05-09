package org.horiga.study.configuration;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(value="jdbc", locations={"classpath:jdbc-settings.yml"})
public class JdbcConnectionSettings extends AbstractSettings {
	private String driver;
	private String url;
	private String username;
	private String password;
	private int maxActive;
	private int minIdel;
	private int maxIdle;
	private int maxWait;
	private String validationQuery = "/* ping */ select 1";
	private Boolean testOnBorrow = false;
	private Boolean testOnReturn = false;
	private Boolean testWhileIdle = true;
	private long timeBetweenEvictionRunsMillis = 60000;
	private int numTestsPerEvictionRun = 5;
	private long minEvictableIdleTimeMillis = -1;
}
