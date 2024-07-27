package com.sales.shopapp.controller;

import com.github.javafaker.Faker;
import com.sales.shopapp.dto.request.ProductDto;
import com.sales.shopapp.dto.request.ProductImageDto;
import com.sales.shopapp.entity.Product;
import com.sales.shopapp.entity.ProductImage;
import com.sales.shopapp.response.ProductListResponse;
import com.sales.shopapp.response.ProductResponse;
import com.sales.shopapp.service.implement.IProductService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.io.UrlResource;
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
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {
    IProductService productService;

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
            files = files == null ? new ArrayList<>() : files;
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
                if (!isImageFile(file)) {
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
            return new ResponseEntity<>("Upload product images successfully", HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<ProductListResponse> getProducts(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0", name = "category_id") Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ) {
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("productId").ascending());
        Page<ProductResponse> productPage = productService.getAllProducts(keyword, categoryId, pageRequest);
        int totalPages = productPage.getTotalPages();
        List<ProductResponse> products = productPage.getContent();
        return ResponseEntity.ok(ProductListResponse
                .builder()
                .productResponseList(products)
                .totalPages(totalPages)
                .build());
    }

//    @GetMapping("/images/{imageName}")
//    public ResponseEntity<?> viewImage(@PathVariable String imageName) {
//        try {
//            java.nio.file.Path imagePath = Paths.get("uploads/" + imageName);
//            UrlResource resource = new UrlResource(imagePath.toUri());
//
//            if (resource.exists()) {
//                return ResponseEntity.ok()
//                        .contentType(MediaType.IMAGE_JPEG)
//                        .body(resource);
//            } else {
//                return ResponseEntity.notFound().build();
//            }
//        } catch (Exception e) {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductsById(@PathVariable("id") Long productId) throws Exception {
        Product existingProduct = productService.getProductById(productId);
        return ResponseEntity.ok(ProductResponse.fromProduct(existingProduct));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProductById(
            @PathVariable("id") Long productId,
            @RequestBody ProductDto productDto)
            throws Exception {
        Product updatedProduct = productService.updateProduct(productId, productDto);
        return ResponseEntity.ok("Update product successfully with product id: " + productId
                + ", category id: " + updatedProduct.getCategoryId());
    }

    @PutMapping("/uploads/{id}")
    public ResponseEntity<?> updateUploadProductById(
            @PathVariable("id") Long productImageId,
            @ModelAttribute MultipartFile files) {

        return ResponseEntity.ok("Update upload product images successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductsById(@PathVariable("id") Long productId) {
        try {
            productService.deleteProduct(productId);
            return ResponseEntity.ok("Delete product with id: " + productId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private String storeFile(MultipartFile file) throws IOException {
        if (!isImageFile(file) || file.getOriginalFilename() == null) {
            throw new IOException("Invalid image format");
        }
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String uniqueFilename = UUID.randomUUID() + "_" + filename;
        java.nio.file.Path uploadDir = Paths.get("uploads");
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        java.nio.file.Path destination = Paths.get(uploadDir.toString(), uniqueFilename);
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
    }

    private boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }

    @PostMapping("/generateFakeProducts")
    public ResponseEntity<String> generateFakeProducts() {
        Faker faker = new Faker();
        for (int i = 0; i < 5000; i++) {
            String productName = faker.commerce().productName();
            String productThumbnail = "https://productname.com";
            if (productService.existByName(productName)) {
                continue;
            }
            ProductDto productDTO = ProductDto.builder()
                    .name(productName)
                    .price((float) faker.number().numberBetween(10, 90_000_000))
                    .description(faker.lorem().sentence(10))
                    .thumbnail(productThumbnail)
                    .categoryId((long) faker.number().numberBetween(3, 4))
                    .build();
            try {
                productService.createProduct(productDTO);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
        return ResponseEntity.ok("Fake Products created successfully");
    }


    @GetMapping("/images/{imageName}")
    public ResponseEntity<?> viewImage(@PathVariable String imageName) {
        try {
            java.nio.file.Path imagePath = Paths.get("uploads/" + imageName);
            UrlResource resource = new UrlResource(imagePath.toUri());
            if (resource.exists()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            } else {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(new UrlResource(Paths.get("uploads/not_found.jpg").toUri()));
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}

