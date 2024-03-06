package com.example.apigateway.filter;

import com.example.apigateway.model.ValidateTokenRequest;
import com.example.apigateway.service.TokenService;
import com.example.apigateway.ulti.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final RouteValidator validator;
    private final JwtUtil jwtUtil;

    private final TokenService tokenService;
    @Autowired
    public AuthenticationFilter(RouteValidator validator, JwtUtil jwtUtil, TokenService tokenService) {
        super(Config.class);
        this.validator = validator;
        this.jwtUtil = jwtUtil;
        this.tokenService = tokenService;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                //header contains token or not
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("missing authorization header");
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                try {
                    ValidateTokenRequest request = new ValidateTokenRequest();
                    request.setToken(authHeader);
                    tokenService.validateToken(request);

                } catch (Exception e) {
                    throw new RuntimeException("un authorized access to application");
                }
            }
            return chain.filter(exchange);
        });
    }

    public static class Config {

    }
}
