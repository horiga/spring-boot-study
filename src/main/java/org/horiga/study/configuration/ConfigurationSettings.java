package org.horiga.study.configuration;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(value="configurations", locations={"classpath:configurations.yml"})
public class ConfigurationSettings extends AbstractSettings {
	private String domain;
	private String phase;
}
