package com.athlas.factory_system.exceptions;

public class WorkerAlreadyAssignedException extends RuntimeException {
    public WorkerAlreadyAssignedException(String message) {
        super(message);
    }
}
