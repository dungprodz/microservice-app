package com.example.productservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.example.productservice.model.ListProductExcel;
import com.example.productservice.model.ResponsePage;
import com.example.productservice.model.document.ProductDocument;
import com.example.productservice.model.request.SearchProductRequest;
import com.example.productservice.model.response.UploadProductResponseBody;
import com.example.productservice.processor.ProductDocumentProcessor;
import com.example.productservice.repository.ProductRepository;
import com.example.productservice.service.ProductService;
import com.example.productservice.util.ElasticSearchUtils;
import com.example.productservice.util.ErrorCode;
import com.example.productservice.util.LargeExcelListener;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);
    private final ProductRepository productRepository;
    private final ProductDocumentProcessor productDocumentProcessor;
    private final ElasticsearchOperations elasticsearchOperations;
    @Value("${spring.elasticsearch.indicates.product.name}")
    private String productIndexName;

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
        } catch (Exception e) {
            LOGGER.error("Exception uploadProduct : ", e);
            throw e;
        }
    }

    @Override
    public ResponsePage<List<ProductDocument>> searchProduct(SearchProductRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Query query = productDocumentProcessor.productQuery(request, pageable);
        SearchHits<ProductDocument> hits =
                elasticsearchOperations.search(query, ProductDocument.class, IndexCoordinates.of(productIndexName));
        return ElasticSearchUtils.searchHitsToPage(hits, pageable);
    }
}
