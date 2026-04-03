// Bezawit Alemu
package com.shopwave.mapper;

import com.shopwave.dto.ProductDTO;
import com.shopwave.model.Product;
import org.springframework.stereotype.Component;

/**
 * Handles conversion between Product entities and ProductDTOs.
 * Keeping mapping logic here instead of in the service keeps things cleaner.
 */
@Component
public class ProductMapper {

    /**
     * Converts a Product entity to a ProductDTO for the API response.
     */
    public ProductDTO toDTO(Product product) {
        if (product == null) {
            return null;
        }

        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        dto.setCreatedAt(product.getCreatedAt());

        // pull category details if the product has one
        if (product.getCategory() != null) {
            dto.setCategoryId(product.getCategory().getId());
            dto.setCategoryName(product.getCategory().getName());
        }

        return dto;
    }
}
