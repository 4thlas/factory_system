package com.athlas.factory_system.exceptions.productExcepions;

public class ProductTypeMismatch extends RuntimeException {
    public ProductTypeMismatch(String message) {
        super(message);
    }
}
