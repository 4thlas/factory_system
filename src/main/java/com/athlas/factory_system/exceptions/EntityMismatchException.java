package com.athlas.factory_system.exceptions;

public class EntityMismatchException extends RuntimeException
{
    public EntityMismatchException(String entity1, String entity2, String fieldName)
    {
        super(entity1 + " and " + entity2 + " have different values for " + fieldName);
    }
}
