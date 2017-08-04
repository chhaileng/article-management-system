package info.chhaileng.app.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@Configuration
public class DatabaseConfiguration {
	@Profile("local_database")
	@Bean
	public DataSource localDb() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl("jdbc:postgresql://127.0.0.1:5432/my_db");
		dataSource.setUsername("postgres");
		dataSource.setPassword("12345");
		this.initializeDatabase(dataSource);
		return dataSource;
	}
	
	@Profile("server_database")
	@Bean
	public DataSource serverDb() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl("jdbc:postgresql://139.59.123.203:5432/spring");
		dataSource.setUsername("postgres");
		dataSource.setPassword("toor");
		return dataSource;
	}
	
	@Value("classpath:db/schema.sql")
	private Resource schema;
	
	@Value("classpath:db/data.sql")
	private Resource data;
	
	private void initializeDatabase(DataSource dataSource) {
		DatabasePopulator populator = new ResourceDatabasePopulator(schema, data);
		DatabasePopulatorUtils.execute(populator, dataSource);
	}
	
}
