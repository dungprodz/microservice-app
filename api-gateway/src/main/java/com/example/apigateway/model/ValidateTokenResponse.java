package com.example.apigateway.model;

import lombok.Data;

@Data
public class ValidateTokenResponse {
    private String responseCode;
    private String responseMessage;
}
