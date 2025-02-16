package com.said.inotekk.crud_customers.Exceptions;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long productId) {
        super("No Product with ID: " + productId);
    }
}