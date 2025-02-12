package com.example.productservice.model.document;

import com.example.productservice.entity.ProductEntity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.data.elasticsearch.annotations.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "#{@agencyIndexName}")
@Setting(settingPath = "/analyzer.json")
public class ProductDocument {
    @Id
    @Field(fielddata = true)
    private String id;
    @Field(type = FieldType.Text, analyzer = "keyword")
    private String productName;
    private BigDecimal price;
    @Field(type = FieldType.Text, analyzer = "keyword")
    private String productCode;
    @Field(type = FieldType.Text, analyzer = "keyword")
    private String categoryId;
    @Field(type = FieldType.Text, analyzer = "keyword")
    private String categoryName;
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
    private LocalDateTime createdDate;
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
    private LocalDateTime updateDate;

    public ProductDocument(ProductEntity productEntity) {
        BeanUtils.copyProperties(productEntity, this);
    }
}
