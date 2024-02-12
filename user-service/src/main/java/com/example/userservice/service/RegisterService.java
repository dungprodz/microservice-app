package com.example.userservice.service;


import com.example.userservice.model.requestbody.RegisterRequestBody;
import com.example.userservice.model.responsebody.RegisterResponseBody;
import org.springframework.http.ResponseEntity;

public interface RegisterService {
    ResponseEntity<RegisterResponseBody> register(RegisterRequestBody requestBody) throws Exception;
}
