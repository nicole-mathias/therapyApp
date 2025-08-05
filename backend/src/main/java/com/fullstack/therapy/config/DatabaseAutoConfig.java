package com.fullstack.therapy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

@Configuration
public class DatabaseAutoConfig {

    @Value("${spring.datasource.url}")
    private String databaseUrl;

    @PostConstruct
    public void configureDatabase() {
        System.out.println("🔧 DatabaseAutoConfig: DATABASE_URL = " + databaseUrl);
        
        // If DATABASE_URL contains postgresql://, set PostgreSQL configuration
        if (databaseUrl != null && databaseUrl.startsWith("postgresql://")) {
            System.setProperty("spring.datasource.driver-class-name", "org.postgresql.Driver");
            System.setProperty("spring.jpa.properties.hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
            System.out.println("✅ Configured for PostgreSQL");
        } else {
            System.setProperty("spring.datasource.driver-class-name", "com.mysql.cj.jdbc.Driver");
            System.setProperty("spring.jpa.properties.hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
            System.out.println("✅ Configured for MySQL");
        }
    }
} 