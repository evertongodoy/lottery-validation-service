package com.lottery.validation.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Data;

import java.util.Base64;
import java.util.Objects;

@Configuration
@ConfigurationProperties(prefix = "evolution.api")
@Data
public class EvolutionApiProperties {
    private String url;
    private String key;
    
    /**
     * Returns the decoded key if it is Base64 encoded, otherwise returns the original key.
     * @return decoded key or original key if not Base64 encoded
     */
    public String getKey() {
        if (Objects.isNull(key)) {
            return key;
        }
        try {
            return new String(Base64.getDecoder().decode(key));
        } catch (IllegalArgumentException e) {
            // If the key is not a valid Base64 string, return it as is
            return key;
        }
    }
}
