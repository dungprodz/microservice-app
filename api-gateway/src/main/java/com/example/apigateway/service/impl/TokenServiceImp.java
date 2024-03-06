package com.example.apigateway.service.impl;

import com.example.apigateway.model.ValidateTokenRequest;
import com.example.apigateway.model.ValidateTokenResponse;
import com.example.apigateway.service.TokenService;
import com.example.apigateway.ulti.CommonRestTemplate;
import com.google.gson.Gson;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImp implements TokenService {
    private final CommonRestTemplate commonRestTemplate;
    private final Gson gson = new Gson();

    public TokenServiceImp(CommonRestTemplate commonRestTemplate) {
        this.commonRestTemplate = commonRestTemplate;
    }

    @Override
    public ValidateTokenResponse validateToken(ValidateTokenRequest validateTokenRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON.toString());
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
        String url = "http://localhost:9002/listener/v1/token/validate";
        String exchange = commonRestTemplate.exchangeCommon(url, HttpMethod.POST,validateTokenRequest,headers);
        ValidateTokenResponse response = gson.fromJson(exchange, ValidateTokenResponse.class);
        return response;
    }
}
