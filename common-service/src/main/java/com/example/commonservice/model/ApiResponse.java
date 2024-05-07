package com.example.commonservice.model;

import com.example.commonservice.util.Common;
import org.springframework.http.HttpStatus;

public class ApiResponse<T> {
    private static final String SUCCEED_CODE = "MSG000000";
    private static final String SUCCEED_MESSAGE = "common.apiResponse.success";
    private final Integer httpStatus;
    private final String code;
    private final String message;
    private final T data;

    private ApiResponse(Integer httpStatus, String code, String message, T data) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponse<T> succeed() {
        return succeed((null));
    }

    public static <T> ApiResponse<T> succeed(T data) {
        return new ApiResponse<T>(HttpStatus.OK.value(), "MSG000000", Common.SUCCESS, data);
    }

    public Integer getHttpStatus() {
        return this.httpStatus;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
    public T getData() {
        return this.data;
    }
}
