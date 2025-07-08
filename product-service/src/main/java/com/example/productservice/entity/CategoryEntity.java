package com.example.productservice.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "category")
@Getter
@Setter
public class CategoryEntity {
    @Id
    @Column(name = "ID")
    private String id;
    @Basic
    @Column(name = "CATEGORY_NAME")
    private String categoryName;
    @Column(name = "DESCRIPTION", columnDefinition = "TEXT")
    private String description;
    @Basic
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;
}
