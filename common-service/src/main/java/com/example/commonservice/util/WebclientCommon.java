package com.example.commonservice.util;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class WebclientCommon {
    private final WebClient webClient;

    public WebclientCommon(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    // GET Mono
    public <T> Mono<T> getMono(String uri, Class<T> responseType, Map<String, String> headers) {
        return webClient.get()
                .uri(uri)
                .headers(httpHeaders -> headers.forEach(httpHeaders::add))
                .retrieve()
                .bodyToMono(responseType);
    }

    // GET Flux
    public <T> Flux<T> getFlux(String uri, Class<T> responseType, Map<String, String> headers) {
        return webClient.get()
                .uri(uri)
                .headers(httpHeaders -> headers.forEach(httpHeaders::add))
                .retrieve()
                .bodyToFlux(responseType);
    }

    // POST Mono
    public <T, R> Mono<T> postMono(String uri, R requestBody, Class<T> responseType, Map<String, String> headers) {
        return webClient.post()
                .uri(uri)
                .headers(httpHeaders -> headers.forEach(httpHeaders::add))
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .bodyToMono(responseType);
    }

    // POST Flux
    public <T, R> Flux<T> postFlux(String uri, R requestBody, Class<T> responseType, Map<String, String> headers) {
        return webClient.post()
                .uri(uri)
                .headers(httpHeaders -> headers.forEach(httpHeaders::add))
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .bodyToFlux(responseType);
    }

    // PUT Mono
    public <T, R> Mono<T> putMono(String uri, R requestBody, Class<T> responseType, Map<String, String> headers) {
        return webClient.put()
                .uri(uri)
                .headers(httpHeaders -> headers.forEach(httpHeaders::add))
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .bodyToMono(responseType);
    }

    // PUT Flux
    public <T, R> Flux<T> putFlux(String uri, R requestBody, Class<T> responseType, Map<String, String> headers) {
        return webClient.put()
                .uri(uri)
                .headers(httpHeaders -> headers.forEach(httpHeaders::add))
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .bodyToFlux(responseType);
    }

    // DELETE Mono
    public <T> Mono<T> deleteMono(String uri, Class<T> responseType, Map<String, String> headers) {
        return webClient.delete()
                .uri(uri)
                .headers(httpHeaders -> headers.forEach(httpHeaders::add))
                .retrieve()
                .bodyToMono(responseType);
    }

    // DELETE Flux
    public <T> Flux<T> deleteFlux(String uri, Class<T> responseType, Map<String, String> headers) {
        return webClient.delete()
                .uri(uri)
                .headers(httpHeaders -> headers.forEach(httpHeaders::add))
                .retrieve()
                .bodyToFlux(responseType);
    }
}
