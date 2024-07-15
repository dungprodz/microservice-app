package com.example.productservice.repository;

import com.example.productservice.entity.ProductEntity;
import com.example.productservice.model.document.ProductDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, String> {
    @Query(value = "SELECT COUNT(p.ID) FROM product p", nativeQuery = true)
    long countProductDataELK();

    List<ProductEntity> findByIdIn(List<String> ids);

    @Query("SELECT new com.example.productservice.model.document.ProductDocument(p) from ProductEntity p")
    Page<ProductDocument> getDataELK(Pageable pageable);
}
