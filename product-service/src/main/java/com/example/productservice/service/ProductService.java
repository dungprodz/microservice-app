package com.example.productservice.service;

import com.example.productservice.model.ResponsePage;
import com.example.productservice.model.document.ProductDocument;
import com.example.productservice.model.request.SearchProductRequest;
import com.example.productservice.model.response.UploadProductResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    UploadProductResponseBody uploadProduct(MultipartFile file) throws IOException;

    ResponsePage<List<ProductDocument>> searchProduct(SearchProductRequest request);
}
