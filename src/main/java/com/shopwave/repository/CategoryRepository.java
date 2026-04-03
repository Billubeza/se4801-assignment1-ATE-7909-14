// Bezawit Alemu
package com.shopwave.repository;

import com.shopwave.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Category entities.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
