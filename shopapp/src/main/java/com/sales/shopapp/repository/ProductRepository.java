package com.sales.shopapp.repository;

import com.sales.shopapp.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name);

//    Page<Product> findAll(Pageable pageable);
}
