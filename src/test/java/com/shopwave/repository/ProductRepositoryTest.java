package com.shopwave.repository;

import com.shopwave.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testFindByNameContainingIgnoreCase() {
        // Arrange
        Product p1 = new Product();
        p1.setName("Gaming Laptop XYZ");
        p1.setPrice(new BigDecimal("1500.00"));
        p1.setStock(5);
        productRepository.save(p1);

        Product p2 = new Product();
        p2.setName("Office Mouse");
        p2.setPrice(new BigDecimal("25.00"));
        p2.setStock(50);
        productRepository.save(p2);

        Product p3 = new Product();
        p3.setName("LAPTOP stand");
        p3.setPrice(new BigDecimal("45.00"));
        p3.setStock(20);
        productRepository.save(p3);

        // Act
        List<Product> results = productRepository.findByNameContainingIgnoreCase("laptop");

        // Assert
        assertEquals(2, results.size());
        
        // Ensure both variations of "laptop" were picked up regardless of casing
        boolean containsGamingLaptop = results.stream().anyMatch(p -> p.getName().equals("Gaming Laptop XYZ"));
        boolean containsLaptopStand = results.stream().anyMatch(p -> p.getName().equals("LAPTOP stand"));
        
        assertTrue(containsGamingLaptop);
        assertTrue(containsLaptopStand);
    }
}
