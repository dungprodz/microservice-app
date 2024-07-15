package com.example.productservice.model.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SearchProductRequest {
    private String categoryCode;
    private String productCode;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private String sort;
    private Integer page;
    private Integer size;
}
