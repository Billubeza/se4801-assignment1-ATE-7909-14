// Bezawit Alemu
package com.shopwave.controller;

import com.shopwave.dto.CreateProductRequest;
import com.shopwave.dto.ProductDTO;
import com.shopwave.dto.StockUpdateRequest;
import com.shopwave.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * REST controller for product operations.
 * All endpoints live under /api/products.
 */
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * GET /api/products?page=0&size=10
     * Returns a paginated list of all products.
     */
    @GetMapping
    public ResponseEntity<Page<ProductDTO>> getAllProducts(Pageable pageable) {
        return ResponseEntity.ok(productService.getAllProducts(pageable));
    }

    /**
     * GET /api/products/{id}
     * Returns the product with the given ID, or 404 if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    /**
     * POST /api/products
     * Creates a new product. Returns 201 Created with the saved product.
     */
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody CreateProductRequest request) {
        ProductDTO created = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * GET /api/products/search?keyword=xxx&maxPrice=100
     * Searches products by keyword and/or max price. Both params are optional.
     */
    @GetMapping("/search")
    public ResponseEntity<List<ProductDTO>> searchProducts(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) BigDecimal maxPrice) {

        return ResponseEntity.ok(productService.searchProducts(keyword, maxPrice));
    }

    /**
     * PATCH /api/products/{id}/stock
     * Adjusts stock by the given delta. Use negative values to decrease stock.
     */
    @PatchMapping("/{id}/stock")
    public ResponseEntity<ProductDTO> updateStock(
            @PathVariable Long id,
            @RequestBody StockUpdateRequest request) {

        return ResponseEntity.ok(productService.updateStock(id, request.getDelta()));
    }
}
