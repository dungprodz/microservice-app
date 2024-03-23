package com.example.productservice.model.response;

import lombok.Data;

@Data
public class UploadProductResponseBody {
    private String errorCode;
    private String errorMessage;
    private String listId;
}
