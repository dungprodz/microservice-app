package com.example.userservice.model.responsebody;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterResponseBody {
    private String status;
    private String message;

    public RegisterResponseBody(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
