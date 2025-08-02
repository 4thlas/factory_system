package com.athlas.factory_system.exceptions.employeeExcepions;

public class WorkerNotAssignedException extends RuntimeException
{
    public WorkerNotAssignedException(Object workerId, Object facilityId)
    {
        super("Worker of id: "+ workerId +" is not assigned to facility of id: "+ facilityId);
    }
}
