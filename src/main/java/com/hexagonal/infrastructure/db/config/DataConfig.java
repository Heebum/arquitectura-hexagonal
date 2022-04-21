package com.hexagonal.infrastructure.db.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@Configuration
//@EnableMongoRepositories(basePackages = "com.hexagonal.infrastructure.db.repository")
@EnableJpaRepositories(basePackages = "com.hexagonal.infrastructure.db.repository")
@ConfigurationProperties("spring.datasource")
@EnableJpaAuditing
@EntityScan(basePackages = "com.hexagonal.infrastructure.db.entity")
public class DataConfig {
}
