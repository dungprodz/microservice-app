package com.example.apigateway.ulti;


import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
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
        protected ClassicHttpRequest createHttpUriRequest(HttpMethod httpMethod, URI uri) {
            return super.createHttpUriRequest(httpMethod, uri);
        }
    }

    public static final class HttpEntityEnclosingGetRequestBase extends HttpEntityEnclosingRequestBase {
        public HttpEntityEnclosingGetRequestBase(final URI uri) {
            super.setURI(uri);
        }
        @Override
        public String getMethod() {
            return HttpMethod.GET.name();
        }
    }
}
