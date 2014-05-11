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
@MapperScan("org.horiga.study")
public class MybatisConfig {
	
	/*
	 * @see
	 *  http://mybatis.github.io/spring/mappers.html
	 */
	
	private static Logger log = LoggerFactory.getLogger(MybatisConfig.class);
	
	@Autowired
	private JdbcConnectionSettings jdbcConnectionSettings;
	
	private DataSource dataSource;
	
	@Bean
	public DataSource dataSource() {
		
		log.debug("> dataSource");
		
		this.dataSource = new BasicDataSource();
		
		log.warn(">>>>>>>>>> jdbc:settings={}", jdbcConnectionSettings);
		
		((BasicDataSource)dataSource).setDriverClassName(jdbcConnectionSettings.getDriver());
		((BasicDataSource)dataSource).setUsername(jdbcConnectionSettings.getUsername());
		((BasicDataSource)dataSource).setPassword(jdbcConnectionSettings.getPassword());
		((BasicDataSource)dataSource).setUrl(jdbcConnectionSettings.getUrl());
		((BasicDataSource)dataSource).setMaxActive(jdbcConnectionSettings.getMaxActive());
		((BasicDataSource)dataSource).setValidationQuery(jdbcConnectionSettings.getValidationQuery());
		((BasicDataSource)dataSource).setTestOnBorrow(jdbcConnectionSettings.getTestOnBorrow());
		((BasicDataSource)dataSource).setTestOnReturn(jdbcConnectionSettings.getTestOnReturn());
		((BasicDataSource)dataSource).setTestWhileIdle(jdbcConnectionSettings.getTestWhileIdle());
		((BasicDataSource)dataSource).setTimeBetweenEvictionRunsMillis(jdbcConnectionSettings.getTimeBetweenEvictionRunsMillis());
		((BasicDataSource)dataSource).setNumTestsPerEvictionRun(jdbcConnectionSettings.getNumTestsPerEvictionRun());
		((BasicDataSource)dataSource).setMinEvictableIdleTimeMillis(jdbcConnectionSettings.getMinEvictableIdleTimeMillis());
		
		return dataSource;
	}
	
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		
		log.debug("> sqlSessionFactory");
		
//		return new SqlSessionFactoryBuilder().build(
//				this.getClass().getResourceAsStream("classpath:mybatis-config.xml"));
		
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
