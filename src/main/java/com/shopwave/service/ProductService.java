// Bezawit Alemu
package com.shopwave.service;

import com.shopwave.dto.CreateProductRequest;
import com.shopwave.dto.ProductDTO;
import com.shopwave.exception.ProductNotFoundException;
import com.shopwave.mapper.ProductMapper;
import com.shopwave.model.Category;
import com.shopwave.model.Product;
import com.shopwave.repository.CategoryRepository;
import com.shopwave.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer for product-related business logic.
 * All write operations are transactional by default; reads use readOnly = true for a small perf boost.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    /**
     * Creates a new product from the given request.
     * Category is optional - a product can exist without one.
     */
    public ProductDTO createProduct(CreateProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());

        // link to a category if one was provided
        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + request.getCategoryId()));
            product.setCategory(category);
        }

        Product saved = productRepository.save(product);
        return productMapper.toDTO(saved);
    }

    /**
     * Returns a paginated list of all products.
     * readOnly since we're just fetching data here.
     */
    @Transactional(readOnly = true)
    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(productMapper::toDTO);
    }

    /**
     * Fetches a single product by ID.
     * Throws ProductNotFoundException if nothing is found.
     */
    @Transactional(readOnly = true)
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return productMapper.toDTO(product);
    }

    /**
     * Searches products by keyword and/or max price.
     * If both are provided, results are intersected.
     * If neither is provided, returns all products.
     */
    @Transactional(readOnly = true)
    public List<ProductDTO> searchProducts(String keyword, BigDecimal maxPrice) {
        List<Product> results;

        if (keyword != null && !keyword.isBlank() && maxPrice != null) {
            // filter by name first, then apply price filter
            results = productRepository.findByNameContainingIgnoreCase(keyword)
                    .stream()
                    .filter(p -> p.getPrice().compareTo(maxPrice) <= 0)
                    .collect(Collectors.toList());

        } else if (keyword != null && !keyword.isBlank()) {
            results = productRepository.findByNameContainingIgnoreCase(keyword);

        } else if (maxPrice != null) {
            results = productRepository.findByPriceLessThanEqual(maxPrice);

        } else {
            results = productRepository.findAll();
        }

        return results.stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Adjusts the stock of a product by a delta value (can be positive or negative).
     * Throws IllegalArgumentException if the result would go below zero.
     */
    public ProductDTO updateStock(Long id, int delta) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        int updatedStock = product.getStock() + delta;
        if (updatedStock < 0) {
            throw new IllegalArgumentException(
                    "Cannot reduce stock below zero. Current stock: " + product.getStock() + ", delta: " + delta);
        }

        product.setStock(updatedStock);
        Product saved = productRepository.save(product);
        return productMapper.toDTO(saved);
    }
}
