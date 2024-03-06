package com.example.apigateway.service;

import com.example.apigateway.model.ValidateTokenRequest;
import com.example.apigateway.model.ValidateTokenResponse;

public interface TokenService {
    ValidateTokenResponse validateToken(ValidateTokenRequest validateTokenRequest);
}
