package com.example.apigateway.filter;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Configuration
public class GatewayConfig {
	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("UserService", r -> r.path("/api/**")
						.filters(f -> f
								// Thêm các filter khác nếu cần
								.modifyResponseBody(String.class, String.class, (exchange, body) -> {
									// Nếu response là lỗi (4xx, 5xx), giữ nguyên body
									if (Objects.requireNonNull(exchange.getResponse().getStatusCode()).isError()) {
										return Mono.just(body); // Trả về response gốc từ service
									}
									// Nếu thành công (2xx), có thể chỉnh sửa response nếu muốn
									return Mono.just(body);
								})
						)
						.uri("http://localhost:9002"))
				.build();
	}
}