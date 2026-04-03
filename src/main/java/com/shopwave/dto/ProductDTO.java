package com.shopwave.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Data transfer object for returning product info to the client.
 * Keeps the API response clean - no JPA stuff leaking out.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;

    // include category name instead of the whole Category object
    private Long categoryId;
    private String categoryName;

    private LocalDateTime createdAt;
}
