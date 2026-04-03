package com.shopwave.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * Represents a product category (e.g., Electronics, Clothing, etc.)
 * Categories are used to group related products together.
 */
@Entity
@Table(name = "categories")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // category name is required
    @NotBlank(message = "Category name cannot be blank")
    @Column(nullable = false)
    private String name;

    // optional description to explain what this category covers
    @Column(columnDefinition = "TEXT")
    private String description;
}
