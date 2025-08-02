package com.athlas.factory_system.exceptions;

public class EntityNotFoundException extends RuntimeException
{
    public EntityNotFoundException(String entity, Object id)
    {
        super(entity + " " + id + " not found.");
    }
}