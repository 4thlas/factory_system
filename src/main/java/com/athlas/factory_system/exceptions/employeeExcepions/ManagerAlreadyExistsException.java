package com.athlas.factory_system.exceptions.employeeExcepions;

public class ManagerAlreadyExistsException extends RuntimeException {
    public ManagerAlreadyExistsException(String message) {
        super(message);
    }
}
