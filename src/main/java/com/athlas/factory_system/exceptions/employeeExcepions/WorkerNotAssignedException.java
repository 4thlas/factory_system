package com.athlas.factory_system.exceptions.employeeExcepions;

public class WorkerNotAssignedException extends RuntimeException {
    public WorkerNotAssignedException(String message)
    {
        super(message);
    }
}
