package com.athlas.factory_system.exceptions.productExcepions;

public class ProductTypeInUse extends RuntimeException
{
    public ProductTypeInUse(String message)
    {
        super(message);
    }
}
