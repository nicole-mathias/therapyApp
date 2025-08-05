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
public class MySQLConfig {
    
    private static final Logger logger = LoggerFactory.getLogger(MySQLConfig.class);
    
    @Value("${spring.datasource.url:}")
    private String databaseUrl;
    
    @Bean
    @Primary
    public DataSource dataSource() {
        String jdbcUrl = databaseUrl;
        
        // If Railway MySQL URL doesn't start with 'jdbc:', add it
        if (databaseUrl != null && !databaseUrl.startsWith("jdbc:")) {
            jdbcUrl = "jdbc:" + databaseUrl;
            logger.info("Converted Railway MySQL URL to JDBC format: {}", jdbcUrl);
        }
        
        return DataSourceBuilder.create()
                .url(jdbcUrl)
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();
    }
} 