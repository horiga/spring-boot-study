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
@MapperScan(basePackages="org.horiga.study.repository")
public class MybatisConfig {
	
	/*
	 * @see
	 *  http://mybatis.github.io/spring/mappers.html
	 */
	
	private static Logger log = LoggerFactory.getLogger(MybatisConfig.class);
	
	@Autowired
	private JdbcConnectionSettings jdbcConnectionSettings;
	
	@Autowired
	private DataSource dataSource;
	
	@Bean
	public DataSource dataSource() {
		
		BasicDataSource ds = new BasicDataSource();
		
		/* FIXME: setup at properties */
//		ds.setDriverClassName(jdbcConnectionSettings.getDriver());
//		ds.setUsername(jdbcConnectionSettings.getUsername());
//		ds.setPassword(jdbcConnectionSettings.getPassword());
//		ds.setUrl(jdbcConnectionSettings.getUrl());
//		ds.setMaxActive(jdbcConnectionSettings.getMaxActive());
//		ds.setValidationQuery(jdbcConnectionSettings.getValidationQuery());
//		ds.setTestOnBorrow(jdbcConnectionSettings.getTestOnBorrow());
//		ds.setTestOnReturn(jdbcConnectionSettings.getTestOnReturn());
//		ds.setTestWhileIdle(jdbcConnectionSettings.getTestWhileIdle());
//		ds.setTimeBetweenEvictionRunsMillis(jdbcConnectionSettings.getTimeBetweenEvictionRunsMillis());
//		ds.setNumTestsPerEvictionRun(jdbcConnectionSettings.getNumTestsPerEvictionRun());
//		ds.setMinEvictableIdleTimeMillis(jdbcConnectionSettings.getMinEvictableIdleTimeMillis());
		
		ds.setDriverClassName("net.sf.log4jdbc.DriverSpy");
		ds.setUsername("root");
		ds.setPassword("1234");
		ds.setUrl("jdbc:log4jdbc:mysql://testdb01:3306/test?characterEncoding=UTF-8&amp;useServerPrepStmts=true&amp;cachePrepStmts=true&amp;connectTimeout=3000&amp;socketTimeout=7000");
		ds.setMaxActive(3);
		ds.setValidationQuery("/* ping */ select 1");
		ds.setTestOnBorrow(false);
		ds.setTestOnReturn(false);
		ds.setTestWhileIdle(true);
		ds.setTimeBetweenEvictionRunsMillis(60000);
		ds.setNumTestsPerEvictionRun(5);
		ds.setMinEvictableIdleTimeMillis(-1);
		
		return ds;
	}
	
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		
		log.debug("> sqlSessionFactory");
		
//		return new SqlSessionFactoryBuilder().build(
//				this.getClass().getResourceAsStream("classpath:mybatis-config.xml"));
		
		final SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean(); 
		sqlSessionFactory.setDataSource(dataSource());
		sqlSessionFactory.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
		sqlSessionFactory.setFailFast(true);
		Resource mapperResource = new ClassPathResource("mapper/study-mapper.xml");
		sqlSessionFactory.setMapperLocations(new Resource[] {mapperResource});
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
