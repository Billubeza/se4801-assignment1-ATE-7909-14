package com.shopwave.exception;

/**
 * Thrown when a product lookup fails - e.g. ID doesn't exist in the database.
 */
public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(Long id) {
        super("Product not found with id: " + id);
    }

    public ProductNotFoundException(String message) {
        super(message);
    }
}
