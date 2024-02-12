package com.example.userservice.controller;


import com.example.userservice.model.requestbody.RegisterRequestBody;
import com.example.userservice.model.responsebody.RegisterResponseBody;
import com.example.userservice.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/kma/v1/register")
public class RegisterController {
    private final RegisterService registerService;
    @Autowired
    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }
    @PostMapping
    public ResponseEntity<RegisterResponseBody> register(@RequestBody RegisterRequestBody requestBody) throws Exception {
        return registerService.register(requestBody);
    }
}
