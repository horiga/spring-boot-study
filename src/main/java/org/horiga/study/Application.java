package org.horiga.study;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableAsync
@EnableConfigurationProperties({ApplicationSettings.class, JdbcConnectionSettings.class, ConfigurationSettings.class})
public class Application implements CommandLineRunner {

	private static Logger log = LoggerFactory.getLogger(Application.class);

	/*
	 * with command line.
	 * --run.env=prod
	 */
	@Value("${run.env}")
	private String environment = "local";
	
	@Autowired
	private ApplicationSettings applicationSettings;
	
	@Autowired
	private JdbcConnectionSettings jdbcConnectionSettings;
	
	public static void main(String[] args) {

		System.out.println("- startup application");

		SpringApplicationBuilder sab = new SpringApplicationBuilder(
				Application.class).addCommandLineProperties(true).showBanner(
				false);
		
		dbg(sab.application());

		sab.application().run(args);

		// SpringApplication.run(Application.class, args);
	}

	private static void dbg(SpringApplication sa) {

		for (ApplicationListener<?> l : sa.getListeners()) {
			log.info("sa.applicationListener={}", l.getClass().getName());
		}

		for (ApplicationContextInitializer<?> i : sa.getInitializers()) {
			log.info("sa.applicationContextInitializer={}, ", i.getClass()
					.getName());
		}

		for (Object s : sa.getSources()) {
			log.info("sa.source={}, ", s.getClass().getName());
		}
	}

	@Override
	public void run(String... arg0) throws Exception {
		System.out.println("spring-boot:command-line-runner startup.");

		log.info("(args) run.environment={}", this.environment);
		
		log.info("(yaml) app.name={}", applicationSettings.getName());
		log.info("(yaml) app.number={}", applicationSettings.getNumber());
		
		log.info("(yaml) jdbc.driver={}", jdbcConnectionSettings.getDriver());
		log.info("(yaml) jdbc.url={}", jdbcConnectionSettings.getUrl());
		log.info("(yaml) jdbc.username={}", jdbcConnectionSettings.getUsername());
		log.info("(yaml) jdbc.password={}", jdbcConnectionSettings.getPassword());
		
		System.out
				.println("spring-boot:command-line-runner startup completed.");
	}

}
