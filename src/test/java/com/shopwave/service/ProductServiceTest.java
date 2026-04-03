// Bezawit Alemu
package com.shopwave.service;

import com.shopwave.dto.CreateProductRequest;
import com.shopwave.dto.ProductDTO;
import com.shopwave.mapper.ProductMapper;
import com.shopwave.model.Category;
import com.shopwave.model.Product;
import com.shopwave.repository.CategoryRepository;
import com.shopwave.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    @Test
    public void testCreateProduct_HappyPath() {
        // Arrange
        CreateProductRequest request = new CreateProductRequest();
        request.setName("Laptop");
        request.setPrice(new BigDecimal("999.99"));
        request.setStock(10);
        request.setCategoryId(1L);

        Category category = new Category();
        category.setId(1L);
        category.setName("Electronics");

        Product savedProduct = new Product();
        savedProduct.setId(100L);
        savedProduct.setName("Laptop");

        ProductDTO returnedDto = new ProductDTO();
        returnedDto.setId(100L);
        returnedDto.setName("Laptop");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);
        when(productMapper.toDTO(savedProduct)).thenReturn(returnedDto);

        // Act
        ProductDTO result = productService.createProduct(request);

        // Assert
        assertEquals(100L, result.getId());
        assertEquals("Laptop", result.getName());
        
        verify(categoryRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(any(Product.class));
        verify(productMapper, times(1)).toDTO(savedProduct);
    }

    @Test
    public void testCreateProduct_CategoryNotFound() {
        // Arrange
        CreateProductRequest request = new CreateProductRequest();
        request.setName("Laptop");
        request.setPrice(new BigDecimal("999.99"));
        request.setStock(10);
        request.setCategoryId(999L);

        when(categoryRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productService.createProduct(request);
        });

        assertEquals("Category not found with id: 999", exception.getMessage());
        
        verify(categoryRepository, times(1)).findById(999L);
        verify(productRepository, never()).save(any(Product.class));
    }
}
