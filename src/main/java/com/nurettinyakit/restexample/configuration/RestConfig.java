package com.nurettinyakit.restexample.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {
    @Bean
    @ConditionalOnMissingBean
    public RestTemplate getRestTemplate(final RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }
}
