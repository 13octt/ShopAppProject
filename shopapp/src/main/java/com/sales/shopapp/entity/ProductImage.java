package com.sales.shopapp.entity;

import jakarta.persistence.*;
import lombok.*;

//@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_images")
@Entity
@Builder
public class ProductImage {
    public static final int MAXIMUM_IMAGES_PER_PRODUCT = 5;
    public static final int MAXIMUM_IMAGES_SIZE = 10 * 1024 * 1024;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_image_id")
    private Long productImageId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product productId;

    @Column(name = "image_url", nullable = false, length = 300)
    private String imageUrl;

}
