package com.athlas.factory_system.exceptions.employeeExcepions;

public class WorkerAlreadyAssignedException extends RuntimeException
{
    public WorkerAlreadyAssignedException(Object workerId, Object facilityId)
    {
        super("Worker of id: "+ workerId +" is already assigned to facility of id: "+ facilityId);
    }
}