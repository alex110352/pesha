package com.example.pesha.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = {"com.example.pesha.dao.entity"})
@EnableJpaRepositories(basePackages = "com.example.pesha.dao.repositories")
public class DaoConfig {
}
