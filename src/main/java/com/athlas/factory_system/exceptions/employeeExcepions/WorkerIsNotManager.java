package com.athlas.factory_system.exceptions.employeeExcepions;

public class WorkerIsNotManager extends RuntimeException
{
    public WorkerIsNotManager(Object workerId)
    {
        super("Worker " + workerId + " is not a manager.");
    }
}
