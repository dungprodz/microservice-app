package com.example.commonservice.ulti;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class WebclientCommon {
    private final WebClient webClient;

    // Constructor với WebClient.Builder để dễ dàng customize
    public WebclientCommon(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .defaultHeader(HttpHeaders.ACCEPT, "application/json")
                .build();
    }

    /**
     * Gửi GET request và nhận Mono response
     */
    public <T> Mono<T> getMono(String uri, Class<T> responseType, Map<String, String> headers) {
        return webClient.get()
                .uri(uri)
                .headers(httpHeaders -> addCustomHeaders(httpHeaders, headers))
                .retrieve()
                .bodyToMono(responseType);
    }

    /**
     * Gửi POST request và nhận Mono response
     */
    public <T, R> Mono<T> postMono(String uri, R requestBody, Class<T> responseType, Map<String, String> headers) {
        return webClient.post()
                .uri(uri)
                .headers(httpHeaders -> addCustomHeaders(httpHeaders, headers))
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .bodyToMono(responseType);
    }

    /**
     * Gửi PUT request và nhận Mono response
     */
    public <T, R> Mono<T> putMono(String uri, R requestBody, Class<T> responseType, Map<String, String> headers) {
        return webClient.put()
                .uri(uri)
                .headers(httpHeaders -> addCustomHeaders(httpHeaders, headers))
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .bodyToMono(responseType);
    }

    /**
     * Gửi DELETE request và nhận Mono response
     */
    public <T> Mono<T> deleteMono(String uri, Class<T> responseType, Map<String, String> headers) {
        return webClient.delete()
                .uri(uri)
                .headers(httpHeaders -> addCustomHeaders(httpHeaders, headers))
                .retrieve()
                .bodyToMono(responseType);
    }

    /**
     * Thêm custom headers vào request
     */
    private void addCustomHeaders(HttpHeaders httpHeaders, Map<String, String> headers) {
        if (headers != null) {
            headers.forEach(httpHeaders::add);
        }
    }
}