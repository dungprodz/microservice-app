package com.example.apigateway.service.impl;

import com.example.apigateway.model.ValidateTokenRequest;
import com.example.apigateway.model.ValidateTokenResponse;
import com.example.apigateway.service.TokenService;
import com.example.apigateway.ulti.ErrorCode;
import com.example.commonservice.util.WebclientCommon;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TokenServiceImp implements TokenService {
    private final WebclientCommon webclientCommon;

    @Override
    public ValidateTokenResponse validateToken(ValidateTokenRequest validateTokenRequest) {
        Map<String, String> headers = new HashMap<>();
        headers.put(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON.toString());
        headers.put(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
        String url = "http://localhost:9002/listener/v1/token/validate";
        ValidateTokenResponse exchange = webclientCommon.postMono(url, validateTokenRequest,
                ValidateTokenResponse.class, headers).block();
        assert exchange != null;
        if (exchange.getResponseCode().equals(ErrorCode.BAD_REQUEST)) {
            throw new RuntimeException("un authorized access to application");
        }
        return exchange;
    }
}
