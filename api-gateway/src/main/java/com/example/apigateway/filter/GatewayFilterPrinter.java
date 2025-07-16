package com.example.apigateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GatewayFilterPrinter implements CommandLineRunner {

	@Autowired
	private List<GatewayFilterFactory<?>> factories;

	@Override
	public void run(String... args) {
		System.out.println("=== GatewayFilterFactories loaded ===");
		factories.forEach(f -> System.out.println(f.name()));
		System.out.println("=====================================");
	}
}
