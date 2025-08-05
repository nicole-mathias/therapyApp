package com.fullstack.therapy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.net.URI;

@Configuration
public class DatabaseConfig {

    @Value("${spring.datasource.url}")
    private String databaseUrl;

    @Bean
    @Primary
    public DataSource dataSource() {
        // Auto-detect database type from DATABASE_URL
        if (databaseUrl != null && databaseUrl.startsWith("postgresql://")) {
            // PostgreSQL configuration
            System.setProperty("spring.jpa.properties.hibernate.dialect", 
                "org.hibernate.dialect.PostgreSQLDialect");
            System.setProperty("spring.datasource.driver-class-name", 
                "org.postgresql.Driver");
        } else {
            // MySQL configuration (default)
            System.setProperty("spring.jpa.properties.hibernate.dialect", 
                "org.hibernate.dialect.MySQLDialect");
            System.setProperty("spring.datasource.driver-class-name", 
                "com.mysql.cj.jdbc.Driver");
        }
        
        return null; // Let Spring Boot auto-configure
    }
} 