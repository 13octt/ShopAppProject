package com.sales.shopapp.service.implement;

import com.sales.shopapp.dto.request.ProductDto;
import com.sales.shopapp.dto.request.ProductImageDto;
import com.sales.shopapp.exception.DataNotFoundException;
import com.sales.shopapp.entity.Product;
import com.sales.shopapp.entity.ProductImage;
import com.sales.shopapp.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface IProductService {
    Product createProduct(ProductDto productDto) throws DataNotFoundException;

    Product getProductById(Long productId) throws DataNotFoundException;

    Page<ProductResponse> getAllProducts(String keyword, Long categoryId, PageRequest pageRequest);

    Product updateProduct(Long productId, ProductDto productDto) throws DataNotFoundException;

    void deleteProduct(Long productId);

    boolean existByName(String name);

    ProductImage createProductImage(Long productId, ProductImageDto productImageDTO) throws Exception;

}
