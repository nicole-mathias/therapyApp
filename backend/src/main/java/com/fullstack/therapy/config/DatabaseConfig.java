package com.fullstack.therapy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import javax.sql.DataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class DatabaseConfig {
    
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);
    
    @Value("${spring.datasource.url:}")
    private String databaseUrl;
    
    @Value("${spring.datasource.username:}")
    private String username;
    
    @Value("${spring.datasource.password:}")
    private String password;
    
    @Value("${spring.datasource.driver-class-name:}")
    private String driverClassName;
    
    @Bean
    @Primary
    @ConditionalOnProperty(name = "spring.datasource.driver-class-name", havingValue = "org.postgresql.Driver")
    public DataSource postgresqlDataSource() {
        logger.info("Configuring PostgreSQL DataSource");
        logger.info("Database URL: {}", databaseUrl);
        logger.info("Driver: {}", driverClassName);
        
        return DataSourceBuilder.create()
                .url(databaseUrl)
                .username(username)
                .password(password)
                .driverClassName(driverClassName)
                .build();
    }
} 