package com.lottery.validation.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "caixa.api")
@Data
public class LotteryApiProperties {
    private String url;
}
