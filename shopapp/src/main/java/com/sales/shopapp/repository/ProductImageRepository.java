package com.sales.shopapp.repository;

import com.sales.shopapp.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    //    List<ProductImage> findByProductId(Long productId);
    List<ProductImage> findByProductImageId(Long productImageId);
}
