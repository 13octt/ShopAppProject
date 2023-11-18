package com.sales.shopapp.controller;

import com.sales.shopapp.dto.CategoryDto;
import com.sales.shopapp.dto.ProductDto;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.print.attribute.standard.Media;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @GetMapping("")
    public ResponseEntity<String> getProducts(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ) {

        return ResponseEntity.ok("product oke");
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getProductsById(@PathVariable("id") String productId) {
        return ResponseEntity.ok("Product with ID: " + productId);
    }

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> insertProducts(
            @Valid @RequestBody ProductDto productDto,
            @RequestPart("file") MultipartFile file,
            BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(fieldError -> fieldError.getDefaultMessage())
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }
            if(file != null){
                if (file.getSize() > 10 * 1024 * 1024) {
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                            .body("File is too large! Maximum is 10MB");
                }

                String contentType = file.getContentType();
                if(contentType == null  || !contentType.startsWith("image/")){
                    return  ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                            .body("File must be an image");
                }
            }
            return ResponseEntity.ok("Product create successfully");
        } catch (Exception e) {
            return ResponseEntity.ok(productDto.toString());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductsById(@PathVariable("id") String productId) {
        return ResponseEntity.ok("Delete product with id: " + productId);
    }

    private String storeFile (MultipartFile file) throws IOException{
        return "sdf";
    }
}
