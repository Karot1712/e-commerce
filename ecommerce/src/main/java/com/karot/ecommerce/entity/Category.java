package com.karot.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    private List<Product> productList;

    @Column(name = "created_at")
    private final LocalDateTime createdAt= LocalDateTime.now();

}
