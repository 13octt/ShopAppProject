package com.sales.shopapp.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_images")
@Entity
@Builder
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_image_id")
    private Long productImageId;

    @ManyToOne(targetEntity = Product.class)
    @JoinColumn(name = "product_id")
    private Product productId;

    @Column(name = "image_url", nullable = false, length = 300)
    private String imageUrl;

}
