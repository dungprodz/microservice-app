package com.example.apigateway.ulti;


import org.apache.http.client.methods.HttpUriRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import java.net.URI;

public final class RestTemplateUtils {

    public static <T> HttpHeaders getRestHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON.toString());
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
        return headers;
    }

    public static final class CustomHttpComponentsClientHttpRequestFactory extends HttpComponentsClientHttpRequestFactory {
        @Override
        protected HttpUriRequest createHttpUriRequest(HttpMethod httpMethod, URI uri) {
            return super.createHttpUriRequest(httpMethod, uri);
        }
    }

}
