package com.example.userservice.service;

import com.example.userservice.model.requestbody.ValidateTokenRequest;
import com.example.userservice.model.responsebody.ValidateTokenResponse;
import com.example.userservice.util.KMAException;

public interface TokenService {
    ValidateTokenResponse validateToken(ValidateTokenRequest validateTokenRequest) throws KMAException;
}
