package com.sales.shopapp.model;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "categories")
@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;
    @Column(name = "name", nullable = false)
    private String name;
}
