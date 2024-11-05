package com.sales.shopapp.repository;

import com.sales.shopapp.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name);

//    Page<Product> findAll(org.springframework.data.domain.Pageable pageable);

//    @Query("SELECT p FROM Product p WHERE " +
//            "( ?1 IS NULL OR ?1 = 0 OR p.categoryId = ?1) AND " +
//            "( ?2 IS NULL OR ?2 = '' OR p.name LIKE CONCAT('%', ?2, '%') OR p.description LIKE CONCAT('%', ?2, '%'))")
//    Page<Product> searchProducts(Long categoryId, String keyword, Pageable pageable);

//    @Query("SELECT * FROM Product p WHERE " +
//            "( ?1 IS NULL OR ?1 = 0 p.categoryId.categoryId = :categoryId)")
//    Page<Product> searchProducts(@Param("categoryId") Long categoryId, @Param("keyword") String keyword, Pageable pageable);

//    @Query("SELECT * FROM Product p WHERE (?1 IS NULL OR ?1 = 0 OR p.categoryId.categoryId = ?1)")
//    Page<Product> searchProducts(@Param("categoryId") Long categoryId, @Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE " +
            "( ?1 IS NULL OR ?1 = 0 OR p.categoryId.categoryId = ?1) AND " +
            "( ?2 IS NULL OR ?2 = '' OR p.name LIKE CONCAT('%', ?2, '%') OR p.description LIKE CONCAT('%', ?2, '%'))")
    Page<Product> searchProducts(Long categoryId, String keyword, Pageable pageable);



//    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.productImages WHERE p.id = :productId")
//    Optional<Product> getDetailProduct(@Param("productId") Long productId);
}
