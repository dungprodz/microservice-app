package com.example.productservice.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "product", schema = "product", catalog = "")
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
    private Timestamp createdDate;
    @Basic
    @Column(name = "UPDATE_DATE")
    private Timestamp updateDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductEntity that = (ProductEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(productName, that.productName) && Objects.equals(price, that.price) && Objects.equals(productCode, that.productCode) && Objects.equals(categoryId, that.categoryId) && Objects.equals(createdDate, that.createdDate) && Objects.equals(updateDate, that.updateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productName, price, productCode, categoryId, createdDate, updateDate);
    }
}
