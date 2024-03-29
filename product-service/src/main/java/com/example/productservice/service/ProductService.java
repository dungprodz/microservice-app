package com.example.productservice.service;

import com.example.productservice.model.response.UploadProductResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {
    UploadProductResponseBody uploadProduct(MultipartFile file) throws IOException;
}
