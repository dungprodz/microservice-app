package com.example.userservice.model.responsebody;

import lombok.Data;

@Data
public class ValidateTokenResponse {
    private String responseCode;
    private String responseMessage;
}
