package com.sales.shopapp.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    Long productId;

    @Column(name = "name", nullable = false, length = 350)
    String name;

    @Column(name = "price")
    float price;

    @Column(name = "thumbnail", length = 300)
    String thumbnail;

    @Column(name = "description")
    String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    Category categoryId;

    @OneToMany(mappedBy = "productId", cascade = CascadeType.REMOVE)
    List<ProductImage> images;
}
