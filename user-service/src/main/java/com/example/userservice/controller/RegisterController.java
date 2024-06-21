package com.example.userservice.controller;


import com.example.userservice.model.requestbody.RegisterRequestBody;
import com.example.userservice.model.responsebody.RegisterResponseBody;
import com.example.userservice.service.RegisterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/user")
@Slf4j
public class RegisterController {
    private final RegisterService registerService;
    @Autowired
    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }
    @PostMapping("/register")
    public ResponseEntity<RegisterResponseBody> register(@RequestBody RegisterRequestBody requestBody) throws Exception {
        return registerService.register(requestBody);
    }
}
