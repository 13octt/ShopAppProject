package com.sales.shopapp.controller;

import com.sales.shopapp.dto.ProductDto;
import com.sales.shopapp.dto.ProductImageDto;
import com.sales.shopapp.entity.Product;
import com.sales.shopapp.entity.ProductImage;
import com.sales.shopapp.exception.DataNotFoundException;
import com.sales.shopapp.repository.ProductImageRepository;
import com.sales.shopapp.repository.ProductRepository;
import com.sales.shopapp.response.ProductListResponse;
import com.sales.shopapp.response.ProductResponse;
import com.sales.shopapp.service.implement.IProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final IProductService productService;
    private final ProductImageRepository productImageRepository;
    @PostMapping("")
    public ResponseEntity<?> createProduct(
            @Valid @RequestBody ProductDto productDTO,
            BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            Product newProduct = productService.createProduct(productDTO);
            return ResponseEntity.ok(newProduct);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "uploads/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImages(
            @PathVariable("id") Long productId,
            @ModelAttribute("files") List<MultipartFile> files
    ) {
        try {
            Product existingProduct = productService.getProductById(productId);
            files = files == null ? new ArrayList<MultipartFile>() : files;
            if (files.size() > ProductImage.MAXIMUM_IMAGES_PER_PRODUCT) {
                return ResponseEntity.badRequest().body("You can only upload maximum 5 images");
            }
            List<ProductImage> productImages = new ArrayList<>();
            for (MultipartFile file : files) {
                if (file.getSize() == 0) {
                    continue;
                }
                if (file.getSize() > ProductImage.MAXIMUM_IMAGES_SIZE) {
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                            .body("File is too large! Maximum size is 10MB");
                }
                if (!isImagesFile(file)) {
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                            .body("File must be an image");
                }
                String filename = storeFile(file);

                ProductImage productImage = productService.createProductImage(
                        existingProduct.getProductId(),
                        ProductImageDto.builder()
                                .imageUrl(filename)
                                .build()
                );
                productImages.add(productImage);
            }
            return ResponseEntity.ok().body(productImages);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<List<ProductListResponse>> getProducts(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ) {
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("createdTime").descending());
        Page<ProductResponse> productsPage = productService.getAllProducts(pageRequest);
        int totalPages = productsPage.getTotalPages();
        List<ProductResponse> products = productsPage.getContent();

        return ResponseEntity.ok(Collections.singletonList(ProductListResponse
                .builder()
                .productResponseList(products)
                .totalPages(totalPages)
                .build()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductsById(@PathVariable("id") Long productId) throws Exception {
        Product existingProduct = productService.getProductById(productId);
        return ResponseEntity.ok(ProductResponse.fromProduct(existingProduct));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProductById(@PathVariable("id") Long productId, ProductDto productDto) {
        // Check xem product with name co ton tai hay khong
        String name = productDto.getName();
        productService.existByName(name);
        // Delete product with cai id do :)))

        return ResponseEntity.ok("Update product successfully");
    }

    @PutMapping("/uploads/{id}")
    public ResponseEntity<?> updateUploadProductById(@PathVariable("id") Long productId){

        return ResponseEntity.ok("Update upload product images successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductsById(@PathVariable("id") Long productId){
        try {
            productService.deleteProduct(productId);
            return ResponseEntity.ok("Delete product with id: " + productId);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private String storeFile(MultipartFile file) throws IOException {
        if (!isImagesFile(file) || file.getOriginalFilename() == null) {
            throw new IOException("Invalid image format");
        }
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;
        java.nio.file.Path uploadDir = Paths.get("uploads");
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        java.nio.file.Path destination = Paths.get(uploadDir.toString(), uniqueFilename);
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
    }

    private boolean isImagesFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }

}

