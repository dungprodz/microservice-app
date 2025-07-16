package com.example.apigateway.service;

import com.example.apigateway.model.ValidateTokenRequest;

public interface TokenService {
    void validateToken(ValidateTokenRequest validateTokenRequest);
}
