package com.fullstack.therapy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

@Configuration
public class RailwayConfig {

    @Value("${spring.datasource.url}")
    private String databaseUrl;

    @PostConstruct
    public void configureRailway() {
        // If DATABASE_URL contains postgresql://, set the railway profile
        if (databaseUrl != null && databaseUrl.startsWith("postgresql://")) {
            System.setProperty("spring.profiles.active", "railway");
        }
    }
} 