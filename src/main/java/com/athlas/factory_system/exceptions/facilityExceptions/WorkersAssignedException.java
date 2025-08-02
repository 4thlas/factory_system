package com.athlas.factory_system.exceptions.facilityExceptions;

import com.athlas.factory_system.entities.Worker;

import java.util.List;
import java.util.stream.Collectors;

public class WorkersAssignedException extends RuntimeException
{
    public WorkersAssignedException(Object facilityId, List<Worker> workers)
    {
        super("Facility of id: "+ facilityId +" has assigned workers of id's: "+
                        workers.stream()
                                .map(worker -> String.valueOf(worker.getId()))
                                .collect(Collectors.joining(", ")));
    }
}