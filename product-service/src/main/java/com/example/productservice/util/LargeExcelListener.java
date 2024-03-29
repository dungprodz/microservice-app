package com.example.productservice.util;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.example.productservice.entity.ProductEntity;
import com.example.productservice.model.ListProductExcel;
import com.example.productservice.repository.ProductRepository;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class LargeExcelListener extends AnalysisEventListener<ListProductExcel> {
    private static final Logger LOGGER = LoggerFactory.getLogger(LargeExcelListener.class);
    private final ProductRepository productRepository;
    private final List<ListProductExcel>  listProductExcels = new ArrayList<>();

    public LargeExcelListener(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void invoke(ListProductExcel product, AnalysisContext context) {
        // Xử lý dữ liệu, data là một hàng từ tệp Excel
        try {
            listProductExcels.add(product);
            if (listProductExcels.size() >= 1000) {
                processAndSaveProducts(listProductExcels);
            }
            } catch(Exception e){
                LOGGER.error("==== Exception  readFileExcel with ex: ====", e);
                throw e;
            }
        }


    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // Xử lý dữ liệu khi đọc xong file
        processAndSaveProducts(listProductExcels);
    }

    private void processAndSaveProducts(List<ListProductExcel>  listProductExcels) {
        List<ProductEntity> productEntityList = listProductExcels.stream().map(item->{
            ProductEntity productEntity = new ProductEntity();
            productEntity.setId(UUID.randomUUID().toString());
            productEntity.setProductName(item.getProductName());
            productEntity.setPrice(item.getPrice());
            productEntity.setProductCode(item.getProductCode());
            return productEntity;
        }).toList();
        productRepository.saveAll(productEntityList);
    }

}
