package com.said.inotekk.crud_customers.Exceptions;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(Long productId) {
        super("Insufficient stock for product with ID: " + productId);
    }
}