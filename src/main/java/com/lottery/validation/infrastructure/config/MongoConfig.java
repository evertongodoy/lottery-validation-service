package com.lottery.validation.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.lottery.validation.infrastructure.adapters.output.persistence.mongodb")
@EnableMongoAuditing
public class MongoConfig {
}
