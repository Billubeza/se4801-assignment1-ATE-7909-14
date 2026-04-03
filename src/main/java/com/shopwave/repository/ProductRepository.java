// Bezawit Alemu
package com.shopwave.repository;

import com.shopwave.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Repository for Product entities.
 * Spring Data JPA generates the SQL for these derived query methods automatically.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // get all products under a given category
    List<Product> findByCategoryId(Long categoryId);

    // used for filtering by a price ceiling
    List<Product> findByPriceLessThanEqual(BigDecimal maxPrice);

    // case-insensitive name search
    List<Product> findByNameContainingIgnoreCase(String keyword);

    // handy for finding the most expensive product
    Optional<Product> findTopByOrderByPriceDesc();
}
