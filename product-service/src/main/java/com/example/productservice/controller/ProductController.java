package com.example.productservice.controller;

import com.example.commonservice.model.ApiResponse;
import com.example.productservice.model.response.UploadProductResponseBody;
import com.example.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    private final ProductService productService;
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/test")
    public String test(){
        return "success";
    }

    @PostMapping ("/upload")
    public ApiResponse<UploadProductResponseBody> uploadProduct(@RequestParam MultipartFile file) throws IOException {
        return ApiResponse.succeed(productService.uploadProduct(file));
    }
}
