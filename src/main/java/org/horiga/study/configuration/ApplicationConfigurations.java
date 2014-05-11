package org.horiga.study.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ApplicationSettings.class, JdbcConnectionSettings.class})
public class ApplicationConfigurations {
}
