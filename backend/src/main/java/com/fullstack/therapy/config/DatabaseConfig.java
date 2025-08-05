package com.fullstack.therapy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class DatabaseConfig {

    @Value("${spring.datasource.url}")
    private String databaseUrl;

    @PostConstruct
    public void configureDatabase() {
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
    }
} 