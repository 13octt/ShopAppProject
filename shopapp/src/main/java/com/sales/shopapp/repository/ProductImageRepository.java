package com.sales.shopapp.repository;

import com.sales.shopapp.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
//    List<ProductImage> findByProductId(Long productId);
    List<ProductImage> findByProductImageId(Long productImageId);
    void deleteByProductImageId(Long productImageId);
}
