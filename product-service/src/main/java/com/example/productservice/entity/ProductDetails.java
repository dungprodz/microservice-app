package com.example.productservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "PRODUCT_DETAILS")
@Getter
@Setter
public class ProductDetails {
	@Id
	@Column(name = "DETAIL_ID")
	private String detailId;

	@Column(name = "PRODUCT_ID")
	private String productId;

	@Column(name = "ATTRIBUTE_NAME")
	private String attributeName;

	@Column(name = "ATTRIBUTE_VALUE")
	private String attributeValue;

	@Column(name = "CREATED_AT")
	private LocalDateTime createdAt;
}
