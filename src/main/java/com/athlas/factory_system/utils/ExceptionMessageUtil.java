package com.athlas.factory_system.utils;

public class ExceptionMessageUtil
{
    public static String notFoundMsg(String entity, Object id)
    {
        return entity +" "+ id +" not found.";
    }

    public static String entityMismatchMsg(String entity1, String entity2)
    {
        return entity1 +" doesn't match "+ entity2;
    }
}
