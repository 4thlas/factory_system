package com.athlas.factory_system.exceptions.employeeExcepions;

public class ManagerAlreadyExistsException extends RuntimeException
{
    public ManagerAlreadyExistsException(Object facilityId, Object managerId)
    {
        super("Facility " + facilityId + " already has a manager (" + managerId + ")");
    }
}
