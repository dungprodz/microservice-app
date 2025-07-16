package com.example.apigateway.filter;

import com.example.apigateway.model.ValidateTokenRequest;
import com.example.apigateway.service.TokenService;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component("Authentication")
public class AuthenticationGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthenticationGatewayFilterFactory.Config> {

    private final RouteValidator validator;
    private final TokenService tokenService;

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                //header contains token or not
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("missing authorization header");
                }

                String authHeader = Objects.requireNonNull(exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                try {
                    ValidateTokenRequest request = new ValidateTokenRequest();
                    request.setToken(authHeader);
                    tokenService.validateToken(request);

                } catch (Exception e) {
                    throw new RuntimeException("unauthorized access to application");
                }
            }
            return chain.filter(exchange);
        });
    }

    public AuthenticationGatewayFilterFactory(RouteValidator validator, TokenService tokenService) {
        super(Config.class);
        this.validator = validator;
        this.tokenService = tokenService;
    }

    public static class Config {
        // Nếu không có properties, vẫn cần class trống
    }
}
