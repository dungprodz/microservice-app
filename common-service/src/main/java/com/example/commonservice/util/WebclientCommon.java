package com.example.commonservice.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class WebclientCommon {
    private final WebClient webClient;

    // GET Mono (một object)
    public <T> Mono<T> getMono(String uri, Class<T> responseType) {
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(responseType);
    }

    // GET Flux (danh sách object)
    public <T> Flux<T> getFlux(String uri, Class<T> responseType) {
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(responseType);
    }

    // POST Mono (trả về 1 object)
    public <T, R> Mono<T> postMono(String uri, R requestBody, Class<T> responseType) {
        return webClient.post()
                .uri(uri)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .bodyToMono(responseType);
    }

    // POST Flux (trả về danh sách object)
    public <T, R> Flux<T> postFlux(String uri, R requestBody, Class<T> responseType) {
        return webClient.post()
                .uri(uri)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .bodyToFlux(responseType);
    }

    // PUT Mono
    public <T, R> Mono<T> putMono(String uri, R requestBody, Class<T> responseType) {
        return webClient.put()
                .uri(uri)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .bodyToMono(responseType);
    }

    // PUT Flux
    public <T, R> Flux<T> putFlux(String uri, R requestBody, Class<T> responseType) {
        return webClient.put()
                .uri(uri)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .bodyToFlux(responseType);
    }

    // DELETE Mono
    public <T> Mono<T> deleteMono(String uri, Class<T> responseType) {
        return webClient.delete()
                .uri(uri)
                .retrieve()
                .bodyToMono(responseType);
    }

    // DELETE Flux
    public <T> Flux<T> deleteFlux(String uri, Class<T> responseType) {
        return webClient.delete()
                .uri(uri)
                .retrieve()
                .bodyToFlux(responseType);
    }
}
