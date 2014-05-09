package org.horiga.study.mybatis;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.horiga.study.configuration.JdbcConnectionSettings;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@MapperScan(basePackages={"org.horiga.study"}, annotationClass=Mapper.class)
public class MybatisConfig {
	/*
	 * @see
	 *  https://github.com/hoserdude/spring-boot-mybatis-profile-sandbox/blob/master/src/main/java/sample/profile/DatabaseConfig.java
	 */
	
	private static Logger log = LoggerFactory.getLogger(MybatisConfig.class);
	
	@Autowired
	private JdbcConnectionSettings jdbcConnectionSettings;
	
	@Autowired
	private DataSource dataSource;
	
	@Bean
	public DataSource dataSource() {
		
		log.debug("> dataSource");
		
		BasicDataSource ds = new BasicDataSource();
		
		ds.setDriverClassName(jdbcConnectionSettings.getDriver());
		ds.setUsername(jdbcConnectionSettings.getUsername());
		ds.setPassword(jdbcConnectionSettings.getPassword());
		ds.setUrl(jdbcConnectionSettings.getUrl());
		ds.setMaxActive(jdbcConnectionSettings.getMaxActive());
		ds.setValidationQuery(jdbcConnectionSettings.getValidationQuery());
		ds.setTestOnBorrow(jdbcConnectionSettings.getTestOnBorrow());
		ds.setTestOnReturn(jdbcConnectionSettings.getTestOnReturn());
		ds.setTestWhileIdle(jdbcConnectionSettings.getTestWhileIdle());
		ds.setTimeBetweenEvictionRunsMillis(jdbcConnectionSettings.getTimeBetweenEvictionRunsMillis());
		ds.setNumTestsPerEvictionRun(jdbcConnectionSettings.getNumTestsPerEvictionRun());
		ds.setMinEvictableIdleTimeMillis(jdbcConnectionSettings.getMinEvictableIdleTimeMillis());
		
		return ds;
	}
	
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		
		log.debug("> sqlSessionFactory");
		
		
		final SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean(); 
		sqlSessionFactory.setDataSource(dataSource());
		sqlSessionFactory.setConfigLocation(new ClassPathResource("classpath:mybatis-config.xml"));
		sqlSessionFactory.setFailFast(true);
		sqlSessionFactory.setMapperLocations(new Resource[] {new ClassPathResource("classpath:mapper/**/*-mapper.xml")});
		sqlSessionFactory.setTypeHandlersPackage("org.horiga.study.mybatis.typehandler");
		return sqlSessionFactory.getObject();
	}
	
	@Bean
	public DataSourceTransactionManager transactionManager() {
		
		log.debug("> transactionManager");
		
		return new DataSourceTransactionManager(dataSource());
	}
	
//	@PostConstruct
//	public void postConstruct() {
//		log.info("jdbc.settings={}", jdbcConnectionSettings);
//	}
}
