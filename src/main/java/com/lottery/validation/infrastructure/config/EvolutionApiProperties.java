package com.lottery.validation.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "evolution.api")
@Data
public class EvolutionApiProperties {
    private String url;
    private String key;
}
