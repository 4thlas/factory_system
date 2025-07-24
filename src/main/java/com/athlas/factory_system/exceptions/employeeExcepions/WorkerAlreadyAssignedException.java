package com.athlas.factory_system.exceptions.employeeExcepions;

public class WorkerAlreadyAssignedException extends RuntimeException {
    public WorkerAlreadyAssignedException(String message) {
        super(message);
    }
}
