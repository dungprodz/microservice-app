package com.example.productservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.example.productservice.model.ListProductExcel;
import com.example.productservice.model.response.UploadProductResponseBody;
import com.example.productservice.repository.ProductRepository;
import com.example.productservice.service.ProductService;
import com.example.productservice.util.ErrorCode;
import com.example.productservice.util.LargeExcelListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public UploadProductResponseBody uploadProduct(MultipartFile file) throws IOException {
        try {
            UploadProductResponseBody responseBody = new UploadProductResponseBody();
            LargeExcelListener listener = new LargeExcelListener(productRepository);
            EasyExcel.read(file.getInputStream(), ListProductExcel.class, listener).sheet().doRead();
            responseBody.setListId(UUID.randomUUID().toString());
            responseBody.setErrorCode(ErrorCode.SUCCESS);
            responseBody.setErrorMessage(ErrorCode.SUCCESS);
            LOGGER.info("responseBody uploadProduct : {}", responseBody);
            return responseBody;
        }catch (Exception e){
            LOGGER.error("Exception uploadProduct : ", e);
            throw e;
        }
    }
}
