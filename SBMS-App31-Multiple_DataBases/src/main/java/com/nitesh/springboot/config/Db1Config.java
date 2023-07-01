package com.nitesh.springboot.config;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
		entityManagerFactoryRef = "userEntityManagerFactory",
		transactionManagerRef = "userTransactionManager",
		basePackages = {
				"com.nitesh.springboot.db1.repo"
				}		
	)
public class Db1Config {
	@Autowired
	private Environment env;
	
	
	@Bean(name = "UserDateSource")
	public DataSource datasource() {
		
		
		DriverManagerDataSource datasource = new DriverManagerDataSource();
		datasource.setUrl(env.getProperty("spring.db1.datasource.url"));
		
		datasource.setUsername(env.getProperty("spring.db1.datasource.usename"));
		datasource.setPassword(env.getProperty("spring.db1.datasource.password"));
		//datasource.setConnectionProperties(env.getProperty("spring.h2.console.enabled"));
		
		return datasource;		
	}
	
	
	@Bean(name = "userEntityManegerFactory")
	public LocalContainerEntityManagerFactoryBean userEntitymanager() {
		
		LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
		
		bean.setDataSource(datasource());
		
		JpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		bean.setJpaVendorAdapter(adapter);
		
		
		HashMap<String, Object> properties = new HashMap<>();
		properties.put("hibernate.hbm2ddl.auto", "create");
		
		properties.put("hibernate.show_sql", "true");
		properties.put("spring.h2.console.enabled", "true");
		
		
		bean.setJpaPropertyMap(properties);
		
		
		
		return bean;
	}
	
	
	@Primary
	@Bean("userTransectionManager")
	public PlatformTransactionManager userTransactionManager(@Qualifier("userEntityManagerFactory") EntityManagerFactory userEntityManager) {
		return new JpaTransactionManager(userEntityManager);
	}
	
	

}
