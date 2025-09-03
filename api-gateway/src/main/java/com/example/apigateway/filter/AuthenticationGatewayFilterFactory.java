package com.example.apigateway.filter;

import com.example.apigateway.model.ValidateTokenRequest;
import com.example.apigateway.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component("Authentication")
@Slf4j
public class AuthenticationGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthenticationGatewayFilterFactory.Config> implements GlobalFilter, Ordered {

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

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
            GatewayFilterChain chain) {
        long start = System.currentTimeMillis();
        var path = exchange.getRequest().getURI().getPath();
        var headers = exchange.getRequest().getHeaders();
        String clientMessageId = "";
        if (headers.containsKey("clientMessageId")) {
            clientMessageId = headers.getFirst("clientMessageId");
        }
        String finalClientMessageId = clientMessageId;

        return chain.filter(exchange)
                .then(Mono.fromRunnable(() -> {
                    long end = System.currentTimeMillis();
                    long time = end - start;
                    String s = finalClientMessageId.isEmpty() ? String.format("API %s : %d ms", path, time)
                            : String.format("clientMessageId %s - API %s : %d ms", finalClientMessageId, path, time);
                    if (time >= 10000) {
                        log.warn(s);
                    } else {
                        log.info(s);
                    }
                }));
    }

    public static class Config {
        // Nếu không có properties, vẫn cần class trống
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
