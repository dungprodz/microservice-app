package com.example.apigateway.ulti;

public interface ErrorCode {
    String SUCCESS = "KMA_200";
    String INTERNAL_SERVER_ERROR = "KMA_500";
    String BAD_REQUEST="KMA_400";
    String DATA_NOT_FOUND ="KMA_404";
    String ACCESS_DENIED ="ACCESS_DENIED";
    String FORBIDDEN = "KMA_403";
    String USER_EXITS = "USER_EXITS";
    String USER_NOT_EXITS = "USER_NOT_EXITS";
    String PASS_WORD_NOT_STRONG = "PASS_WORD_NOT_STRONG";
}
