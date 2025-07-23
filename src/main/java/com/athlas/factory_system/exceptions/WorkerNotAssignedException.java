package com.athlas.factory_system.exceptions;

public class WorkerNotAssignedException extends RuntimeException {
    public WorkerNotAssignedException(String message)
    {
        super(message);
    }
}
