package com.athlas.factory_system.exceptions.productExcepions;

public class ProductTypeAlreadyExists extends RuntimeException {
    public ProductTypeAlreadyExists(String message) {
        super(message);
    }
}
