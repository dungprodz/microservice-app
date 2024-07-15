package com.example.productservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ElasticsearchIndices {
    @Value("${spring.elasticsearch.indicates.product.name}")
    private String productIndexName;

    @Bean
    public String productIndexName() {
        return productIndexName;
    }
}
