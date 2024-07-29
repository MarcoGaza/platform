package cn.econets.blossom.framework.test.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.sql.init.SqlInitializationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.init.DataSourceScriptDatabaseInitializer;
import org.springframework.boot.sql.init.AbstractScriptDatabaseInitializer;
import org.springframework.boot.sql.init.DatabaseInitializationSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import javax.sql.DataSource;

/**
 * SQL Initialization test Configuration
 *
 * Why not use org.springframework.boot.autoconfigure.sql.init.DataSourceInitializationConfiguration ,？
 * Because we will use it in unit testing spring.main.lazy-initialization for true，Enable delayed loading。At this time，Will lead to DataSourceInitializationConfiguration Initialization
 * But，The implementation code of the current class，Basically copy DataSourceInitializationConfiguration De ha！
 *
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnMissingBean(AbstractScriptDatabaseInitializer.class)
@ConditionalOnSingleCandidate(DataSource.class)
@ConditionalOnClass(name = "org.springframework.jdbc.datasource.init.DatabasePopulator")
@Lazy(value = false) // Disable delayed loading
@EnableConfigurationProperties(SqlInitializationProperties.class)
public class SqlInitializationTestConfiguration {

	@Bean
	public DataSourceScriptDatabaseInitializer dataSourceScriptDatabaseInitializer(DataSource dataSource,
																				   SqlInitializationProperties initializationProperties) {
		DatabaseInitializationSettings settings = createFrom(initializationProperties);
		return new DataSourceScriptDatabaseInitializer(dataSource, settings);
	}

	static DatabaseInitializationSettings createFrom(SqlInitializationProperties properties) {
		DatabaseInitializationSettings settings = new DatabaseInitializationSettings();
		settings.setSchemaLocations(properties.getSchemaLocations());
		settings.setDataLocations(properties.getDataLocations());
		settings.setContinueOnError(properties.isContinueOnError());
		settings.setSeparator(properties.getSeparator());
		settings.setEncoding(properties.getEncoding());
		settings.setMode(properties.getMode());
		return settings;
	}

}
