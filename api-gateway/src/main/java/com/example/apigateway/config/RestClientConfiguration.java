package com.example.apigateway.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestClientConfiguration {

    // First Method: default
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

}
