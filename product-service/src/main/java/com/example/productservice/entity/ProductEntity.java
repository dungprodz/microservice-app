package com.example.productservice.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "product")
@Getter
@Setter
public class ProductEntity {
    @Id
    @Column(name = "ID")
    private String id;
    @Basic
    @Column(name = "PRODUCT_NAME")
    private String productName;
    @Basic
    @Column(name = "PRICE")
    private BigDecimal price;
    @Basic
    @Column(name = "PRODUCT_CODE")
    private String productCode;
    @Basic
    @Column(name = "CATEGORY_ID")
    private String categoryId;
    @Basic
    @Column(name = "CREATED_DATE")
    @CreationTimestamp
    private LocalDateTime createdDate;
    @Basic
    @Column(name = "UPDATE_DATE")
    @UpdateTimestamp
    private LocalDateTime updateDate;
}
