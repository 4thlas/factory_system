package com.athlas.factory_system.exceptions.employeeExcepions;

public class WorkerIsNotManager extends RuntimeException {
    public WorkerIsNotManager(String message) {
        super(message);
    }
}
