package com.nitesh.springboot.config;

import java.util.HashMap;

import javax.sql.DataSource;


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
		entityManagerFactoryRef = "ProductEntityManagerFactory",
		transactionManagerRef = "ProductTransactionManager",
		basePackages = {
				"com.nitesh.springboot.db2.repo"
				}		
	)
public class Db2Config {
	
	
	@Autowired
	private Environment env;

	

	@Bean(name = "ProductDateSource")
	@ConfigurationProperties(prefix = "spring.db2.datasource")
	public DataSource data2source() {	
		
		DriverManagerDataSource datasource = new DriverManagerDataSource();
		datasource.setUrl(env.getProperty("spring.db2.datasource.url"));
		datasource.setUsername(env.getProperty("spring.db2.datasource.username"));
		datasource.setPassword(env.getProperty("spring.db2.datasource.password"));
		
		return datasource;
	}
	
	
	
	
	
	@Bean(name = "ProductEntityManegerFactory")
	public LocalContainerEntityManagerFactoryBean productEntitymanager() {
		
		LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
		
		bean.setDataSource(data2source());
		
		JpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		bean.setJpaVendorAdapter(adapter);
		
		
		HashMap<String, Object> properties = new HashMap<>();
		properties.put("hibernate.hbm2ddl.auto", "create");
		properties.put("hibernate.show_sql", "true");
		
		bean.setJpaPropertyMap(properties);
		bean.setPackagesToScan("com.nitesh.springboot.db1.entities");
		
		
		return bean;
		
	}
	
	
	@Bean("ProductTransectionManager")
	public PlatformTransactionManager productTransactionManager(@Qualifier("ProductEntityManagerFactory") EntityManagerFactory productEntityManager) {
		return new JpaTransactionManager(productEntityManager);
	}
	
	

	
	
}
