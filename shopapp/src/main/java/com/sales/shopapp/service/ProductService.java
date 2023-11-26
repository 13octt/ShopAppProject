package com.sales.shopapp.service;

import com.sales.shopapp.dto.ProductDto;
import com.sales.shopapp.dto.ProductImageDto;
import com.sales.shopapp.exception.DataNotFoundException;
import com.sales.shopapp.exception.InvalidParamException;
import com.sales.shopapp.model.Category;
import com.sales.shopapp.model.Product;
import com.sales.shopapp.model.ProductImage;
import com.sales.shopapp.repository.CategoryRepository;
import com.sales.shopapp.repository.ProductImageRepository;
import com.sales.shopapp.repository.ProductRepository;
import com.sales.shopapp.service.implement.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;

    @Override
    public Product createProduct(ProductDto productDto) throws DataNotFoundException {
        Category existingCategory = categoryRepository.findById(Long.valueOf(productDto.getCategoryId()))
                .orElseThrow(() -> new DataNotFoundException("Can not find category with id: " + productDto.getCategoryId()));

        Product newProduct = Product.builder()
                .name(productDto.getName())
                .price(productDto.getPrice())
                .thumbnail(productDto.getThumbnail())
                .categoryId(existingCategory)
                .build();

        return productRepository.save(newProduct);
    }

    @Override
    public Product getProductById(Long productId) throws DataNotFoundException {
        return productRepository.findById(productId).
                orElseThrow(() -> new DataNotFoundException("Can not find this product with id "+ productId));
    }

    @Override
    public Page<Product> getAllProducts(PageRequest pageRequest) {

        return productRepository.findAll(pageRequest);
    }

    @Override
    public Product updateProduct(Long productId, ProductDto productDto) throws DataNotFoundException {
        Product existingProduct = getProductById(productId);
        if(existingProduct != null){
            // Copy cac thuoc tinh tu DTO -> Product
            // co the sd Modelmapper
            Category existingCategory = categoryRepository.findById(Long.valueOf(productDto.getCategoryId()))
                    .orElseThrow(() -> new DataNotFoundException("Can not find category with id: " + productDto.getCategoryId()));
            existingProduct.setName(productDto.getName());
            existingProduct.setCategoryId(existingCategory);
            existingProduct.setDescription(productDto.getDescription());
            existingProduct.setThumbnail(productDto.getThumbnail());
            return productRepository.save(existingProduct);

        }
        return null;
    }

    @Override
    public void deleteProduct(Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        productOptional.ifPresent(productRepository::delete);
    }

    @Override
    public boolean existByName(String name) {
        return productRepository.existsByName(name);
    }

    @Override
    public ProductImage createProductImage(Long productId, ProductImageDto productImageDto) throws Exception {
        Product existingProduct = productRepository
                .findById(productImageDto.getProductId())
                .orElseThrow(()-> new DataNotFoundException("Cannot find product with id: " + productImageDto.getProductId()));
        ProductImage newProductImages = ProductImage.builder()
                .productId(existingProduct)
                .imageUrl(productImageDto.getImageUrl())
                .build();

        int size = productImageRepository.findByProductImageId(productId).size();
        if(size >= 5){
            throw new InvalidParamException("Number of images must be <= 5");
        }
        return productImageRepository.save(newProductImages);
    }
}
