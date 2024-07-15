package com.example.productservice.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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
    @Column(name = "CATEGORY_ID")
    private String categoryCode;
    @Basic
    @Column(name = "CATEGORY_NAME")
    private String categoryName;
    @Basic
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;
}
