package com.example.apigateway.ulti;

import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.TrustStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.security.cert.X509Certificate;
import java.time.Duration;
import java.util.Collections;
import java.util.Objects;

@Component
public class CommonRestTemplate {

    private final RestTemplate restTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonRestTemplate.class);

    @Autowired
    public CommonRestTemplate(RestTemplateBuilder templateBuilder) {
        this.restTemplate = templateBuilder
                .setConnectTimeout(Duration.ofMillis(15000))
                .setReadTimeout(Duration.ofMillis(15000))
                .build();
    }

    public <T> String exchangeCommon(String url, HttpMethod httpMethod, T requestBody,HttpHeaders headers) {
        try {
            LOGGER.warn("=== Start CommonRestTemplate exchangeCommon ===");

            restTemplate.setRequestFactory(disableSSL());
            if(Objects.nonNull(requestBody) && HttpMethod.GET.name().equalsIgnoreCase(httpMethod.name())){
                //tuong hop http get co body
                restTemplate.setRequestFactory(new RestTemplateUtils.CustomHttpComponentsClientHttpRequestFactory());
                headers = RestTemplateUtils.getRestHeader();
            }
            HttpEntity<T> httpEntity = new HttpEntity<>(requestBody, headers);
            MappingJackson2HttpMessageConverter messageConverter = getMessageConverter();
            restTemplate.getMessageConverters().add(messageConverter);
            ResponseEntity<Object> exchange = restTemplate.exchange(url, httpMethod, httpEntity, Object.class);
            LOGGER.warn("====CommonRestTemplate exchangeCommon with exchange: {}=== ", exchange);
            return new Gson().toJson(exchange.getBody());
        } catch (HttpClientErrorException httpError) {
            LOGGER.error("=== CommonRestTemplate exchangeCommon failed. Response StatusCode: {}, ErrorResponse: {} ===", httpError.getStatusCode().value(), httpError.getResponseBodyAsString());
            throw httpError;
        }catch (ResourceAccessException | HttpServerErrorException ex) {
            LOGGER.error("=== CommonRestTemplate exchangeCommon exchange exception {}", ex.getMessage());
            throw ex;
        }
        catch (Exception e) {
            LOGGER.error("=== CommonRestTemplate exchangeCommon failed with error ===", e);
            throw e;
        }
    }

    private MappingJackson2HttpMessageConverter getMessageConverter() {
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        messageConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        return messageConverter;
    }

    @SneakyThrows
    private HttpComponentsClientHttpRequestFactory disableSSL() {

        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
        SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
                .loadTrustMaterial(null, acceptingTrustStrategy)
                .build();
        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);
        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(csf)
                .build();
        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient((HttpClient) httpClient);
        return requestFactory;

    }
}