package com.sales.shopapp.service;

import com.sales.shopapp.dto.request.ProductDto;
import com.sales.shopapp.dto.request.ProductImageDto;
import com.sales.shopapp.exception.DataNotFoundException;
import com.sales.shopapp.exception.InvalidParamException;
import com.sales.shopapp.entity.Category;
import com.sales.shopapp.entity.Product;
import com.sales.shopapp.entity.ProductImage;
import com.sales.shopapp.repository.CategoryRepository;
import com.sales.shopapp.repository.ProductImageRepository;
import com.sales.shopapp.repository.ProductRepository;
import com.sales.shopapp.response.ProductResponse;
import com.sales.shopapp.service.implement.IProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public Product createProduct(ProductDto productDto) throws DataNotFoundException {
        Category existingCategory = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new DataNotFoundException
                        ("Can not find category with id: " + productDto.getCategoryId()));

        Product newProduct = Product.builder()
                .name(productDto.getName())
                .price(productDto.getPrice())
                .thumbnail(productDto.getThumbnail())
                .description(productDto.getDescription())
                .categoryId(existingCategory)
                .build();

        return productRepository.save(newProduct);
    }

    @Override
    public Product getProductById(Long productId) throws DataNotFoundException {
        return productRepository.findById(productId).
                orElseThrow(() -> new DataNotFoundException("Can not find this product with id " + productId));
    }

    @Override
    public Page<ProductResponse> getAllProducts(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest).map(ProductResponse::fromProduct);
    }

    @Override
    @Transactional
    public Product updateProduct(Long productId, ProductDto productDto) throws DataNotFoundException {
        Product product = getProductById(productId);
        if (product != null) {
            // (Model Mapper)
            Category existingCategory = categoryRepository.findById(productDto.getCategoryId()).
                    orElseThrow(() -> new DataNotFoundException
                            ("Can not find category with id: " + productDto.getCategoryId()));

            modelMapper.typeMap(ProductDto.class, Product.class)
                    .addMappings(mapper -> mapper.skip(Product::setProductId));
            modelMapper.map(productDto, product);

//            existingProduct.setName(productDto.getName());
            product.setCategoryId(existingCategory);
//            existingProduct.setDescription(productDto.getDescription());
//            existingProduct.setThumbnail(productDto.getThumbnail());
//            existingProduct.setPrice(productDto.getPrice());
            return productRepository.save(product);
        }
        return null;
    }

    @Override
    @Transactional
    public void deleteProduct(Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        productOptional.ifPresent(productRepository::delete);
    }

    @Override
    public boolean existByName(String name) {
        return productRepository.existsByName(name);
    }

    @Override
    @Transactional
    public ProductImage createProductImage(Long productId, ProductImageDto productImageDto) throws Exception {
        Product existingProduct = productRepository
                .findById(productId).orElseThrow(() ->
                        new DataNotFoundException("Cannot find product with id: " + productImageDto.getProductId()));
        ProductImage newProductImages = ProductImage.builder()
                .productId(existingProduct)
                .imageUrl(productImageDto.getImageUrl())
                .build();

        int size = productImageRepository.findByProductImageId(productId).size();
        if (size >= ProductImage.MAXIMUM_IMAGES_PER_PRODUCT) {
            throw new InvalidParamException("Number of images must be <= " + ProductImage.MAXIMUM_IMAGES_PER_PRODUCT);
        }
        return productImageRepository.save(newProductImages);
    }
}
