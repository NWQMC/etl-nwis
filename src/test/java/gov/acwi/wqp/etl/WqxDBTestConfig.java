package gov.acwi.wqp.etl;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import com.github.springtestdbunit.bean.DatabaseConfigBean;
import com.github.springtestdbunit.bean.DatabaseDataSourceConnectionFactoryBean;

@TestConfiguration
public class WqxDBTestConfig {
	@Autowired
	@Qualifier("dbUnitWqxDatabaseConfig")
	private DatabaseConfigBean dbUnitWqxDatabaseConfig;

	@Autowired
	@Qualifier("dataSourceWqx")
	private DataSource dataSourceWqx;

	@Bean
	public DatabaseConfigBean dbUnitWqxDatabaseConfig() {
		DatabaseConfigBean dbUnitDbConfig = new DatabaseConfigBean();
		// Currently the WQX tables from EPA are in upper case.
		dbUnitDbConfig.setCaseSensitiveTableNames(true);
		// And need to be enclosed in quotes for the delete portion of the database
		// setup.
		dbUnitDbConfig.setEscapePattern("\"?\"");
		return dbUnitDbConfig;
	}

	@Bean
	@ConfigurationProperties(prefix = EtlConstantUtils.SPRING_DATASOURCE_WQX)
	public DataSourceProperties dataSourcePropertiesWqx() {
		return new DataSourceProperties();
	}

	@Bean
	public DataSource dataSourceWqx() {
		return dataSourcePropertiesWqx().initializeDataSourceBuilder().build();
	}

	@Bean
	public JdbcTemplate jdbcTemplateWqx() {
		return new JdbcTemplate(dataSourceWqx);
	}

	@Bean
	public DatabaseDataSourceConnectionFactoryBean wqx() throws SQLException {
		DatabaseDataSourceConnectionFactoryBean dbUnitDatabaseConnection = new DatabaseDataSourceConnectionFactoryBean();
		dbUnitDatabaseConnection.setDatabaseConfig(dbUnitWqxDatabaseConfig);
		dbUnitDatabaseConnection.setDataSource(dataSourceWqx);
		dbUnitDatabaseConnection.setSchema("wqx");
		return dbUnitDatabaseConnection;
	}

	@Bean
	public DatabaseDataSourceConnectionFactoryBean wqxDump() throws SQLException {
		DatabaseDataSourceConnectionFactoryBean dbUnitDatabaseConnection = new DatabaseDataSourceConnectionFactoryBean();
		dbUnitDatabaseConnection.setDatabaseConfig(dbUnitWqxDatabaseConfig);
		dbUnitDatabaseConnection.setDataSource(dataSourceWqx);
		dbUnitDatabaseConnection.setSchema("wqx_dump");
		return dbUnitDatabaseConnection;
	}

}
