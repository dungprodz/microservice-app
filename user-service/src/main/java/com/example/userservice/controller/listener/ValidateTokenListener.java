package com.example.userservice.controller.listener;

import com.example.userservice.model.requestbody.ValidateTokenRequest;
import com.example.userservice.model.responsebody.ValidateTokenResponse;
import com.example.userservice.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/listener/v1/token")
public class ValidateTokenListener {

    private final TokenService tokenService;
    @Autowired
    public ValidateTokenListener(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/validate")
    public ResponseEntity<ValidateTokenResponse> register(@RequestBody ValidateTokenRequest requestBody) throws Exception {
        return new ResponseEntity<>(tokenService.validateToken(requestBody), HttpStatus.OK);
    }
}
