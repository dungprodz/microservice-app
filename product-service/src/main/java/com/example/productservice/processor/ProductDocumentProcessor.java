package com.example.productservice.processor;

import com.example.commonservice.common.ContextUtils;
import com.example.commonservice.common.TimePattern;
import com.example.productservice.entity.ProductEntity;
import com.example.productservice.model.document.ProductDocument;
import com.example.productservice.model.request.SearchProductRequest;
import com.example.productservice.repository.ProductDocumentRepository;
import com.example.productservice.repository.ProductRepository;
import com.example.productservice.util.ElasticSearchUtils;
import com.example.productservice.util.TimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductDocumentProcessor {

    private final ProductDocumentRepository productDocumentRepository;
    private final ProductRepository productRepository;
    private static final String LOCAL = "local";

    @EventListener(ApplicationReadyEvent.class)
    public void reloadDataELK() {
        boolean initOnStartup = ContextUtils.getBean(Environment.class)
                .getProperty("spring.elasticsearch.indicates.product.init-on-startup", Boolean.class,
                        false);
        if (!initOnStartup) {
            return;
        }
        ContextUtils.getExecutor().execute(() -> {
            log.info("Start init quotation elk data...");
            long start = System.currentTimeMillis();
            int page = 0;
            int size = 3000;

            productDocumentRepository.deleteAll();

            long totalRow = productRepository.countProductDataELK();
            long totalPage = totalRow / size;
            if (totalRow % size != 0) {
                totalPage++;
            }

            while (true) {
                List<ProductDocument> productDocuments =
                        productRepository.getDataELK(PageRequest.of(page, size)).getContent();

                productDocumentRepository.saveAll(productDocuments);

                if (page == totalPage) {
                    break;
                }

                double completed = (double) (page + 1) / totalPage;
                log.info("Reloading data elk process... {}%", round(completed * 100d));
                page++;
            }
            log.info("Reloaded data elk success, exec time: {}",
                    System.currentTimeMillis() - start);

        });
    }

    public double round(double value, int places, RoundingMode mode) {
        if (places < 0) {
            throw new IllegalArgumentException();
        } else {
            return BigDecimal.valueOf(value).setScale(places, mode).doubleValue();
        }
    }

    public double round(double value) {
        return round(value, 2);
    }

    public double round(double value, int places) {
        return round(value, places, RoundingMode.HALF_UP);
    }

    public void updateDocument(List<String> ids) {
        boolean breakWhile = false;
        int batch = 999;
        List<ProductEntity> productEntityList = new ArrayList<>();
        do {
            List<String> existIds = new ArrayList<>();
            if (ids.size() >= batch) {
                existIds.addAll(ids.subList(0, batch));
                ids.removeAll(existIds);
            } else {
                existIds.addAll(ids);
                breakWhile = true;
            }
            productEntityList.addAll(productRepository.findByIdIn(existIds));
        } while (!breakWhile);

        productDocumentRepository.saveAll(productEntityList.stream().map(ProductDocument::new).collect(Collectors.toList()));
    }

    public Query productQuery(SearchProductRequest searchProductRequest, Pageable pageable) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (ObjectUtils.isNotEmpty(searchProductRequest.getFromDate()) || ObjectUtils.isNotEmpty(searchProductRequest.getToDate())) {

            RangeQueryBuilder dateRangeQuery = QueryBuilders.rangeQuery("createdAt");

            if (ObjectUtils.isNotEmpty(searchProductRequest.getFromDate())) {
                dateRangeQuery.gte(TimeUtils.timeToString(searchProductRequest.getFromDate(),
                        TimePattern.YYYY_MM_DD_T_HH_MM_SS));
            }

            if (ObjectUtils.isNotEmpty(searchProductRequest.getToDate())) {
                dateRangeQuery.lte(TimeUtils.timeToString(searchProductRequest.getToDate(),
                        TimePattern.YYYY_MM_DD_T_HH_MM_SS));
            }
            boolQueryBuilder.must(dateRangeQuery);
        }
        ElasticSearchUtils.mustTerm(boolQueryBuilder, "categoryCode", searchProductRequest.getCategoryCode());
        ElasticSearchUtils.mustTerm(boolQueryBuilder, "productCode", searchProductRequest.getProductCode());

        Query searchQuery = new NativeSearchQueryBuilder().withQuery(boolQueryBuilder).withPageable(pageable)
                .withSorts(SortBuilders.fieldSort(searchProductRequest.getSort()).order(SortOrder.DESC))
                .withTrackTotalHits(true).build();

        searchQuery.setPreference(LOCAL);
        return searchQuery;
    }
}
