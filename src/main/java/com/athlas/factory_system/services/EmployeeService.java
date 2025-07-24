package com.athlas.factory_system.services;

import com.athlas.factory_system.entities.Worker;
import com.athlas.factory_system.exceptions.employeeExcepions.ManagerAlreadyExistsException;
import com.athlas.factory_system.exceptions.employeeExcepions.WorkerAlreadyAssignedException;
import com.athlas.factory_system.exceptions.employeeExcepions.WorkerIsNotManager;
import com.athlas.factory_system.exceptions.employeeExcepions.WorkerNotAssignedException;
import com.athlas.factory_system.repositories.FacilityRepository;
import com.athlas.factory_system.repositories.WorkerRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Setter
@Getter
@AllArgsConstructor
public class EmployeeService
{
    private final FacilityService facilityService;
    private final FacilityRepository facilityRepository;
    private final WorkerRepository workerRepository;

    @Transactional
    public void addWorker(int facilityId, String name, String role, int paycheck)
    {
        var assignedFacility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new EntityNotFoundException("Facility of id: "+ facilityId + " not found."));

        var newWorker = Worker.builder()
                .facility(assignedFacility)
                .name(name)
                .role(role)
                .paycheck(BigDecimal.valueOf(paycheck))
                .build();

        workerRepository.save(newWorker);
        assignedFacility.getWorkers().add(newWorker);
    }

    @Transactional
    public void removeWorker(int workerId)
    {
        var worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new EntityNotFoundException("Worker of id: "+ workerId +" not found."));

        // If user was manager of facility, unassign
        if (worker.getFacility().getManager() != null && worker.getFacility().getManager() == worker)
        {
            unassignManager(worker.getFacility().getId());
        }

        workerRepository.delete(worker);
    }

    @Transactional
    public void moveWorker(int workerId, int targetFacilityId)
    {
        var worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new EntityNotFoundException("Worker of id: "+ workerId +" not found."));

        var targetFacility = facilityRepository.findById(targetFacilityId)
                .orElseThrow(() -> new EntityNotFoundException("Facility of id: "+ targetFacilityId +" not found."));

        // Check if user is not assigned to target facility
        if (worker.getFacility() != targetFacility)
        {
            // If user was manager of previous facility, unassign
            if (worker.getFacility().getManager() != null && worker.getFacility().getManager() == worker)
            {
                unassignManager(worker.getFacility().getId());
            }

            worker.setFacility(targetFacility);

            worker.getFacility().getWorkers().remove(worker); // Remove worker from previous facility entity
            targetFacility.getWorkers().add(worker);
        }
        else {
            throw new WorkerAlreadyAssignedException("Worker of id: "+ workerId +" is already assigned to facility of id: "+ targetFacilityId);
        }
    }

    @Transactional
    public void assignManager(int workerId, int facilityId)
    {
        var worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new EntityNotFoundException("Worker of id: "+ workerId +" not found."));
        
        var facility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new EntityNotFoundException("Facility of id: "+ facilityId +" not found."));

        // Check if a worker is assigned to the facility
        if (worker.getFacility() == facility)
        {
            // Check if worker is manager
            if (worker.getRole().equalsIgnoreCase("manager"))
            {
                // Check if the facility doesn't have a manager
                if (facility.getManager() == null)
                {
                    facility.setManager(worker);
                }
                else {
                    throw new ManagerAlreadyExistsException("Facility " + facility.getId() + " already has a manager (" + facility.getManager().getId() + ")");
                }
            }
            else {
                throw new WorkerIsNotManager("Worker of id: "+ workerId +" is not a manager.");
            }
        }
        else {
            throw new WorkerNotAssignedException("Worker of id: "+ workerId +" is not assigned to facility of id: "+ facilityId);
        }
    }

    @Transactional
    public void unassignManager(int facilityId)
    {
        var facility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new EntityNotFoundException("Facility of id: "+ facilityId +" not found."));

        // Unset manager in facility entity
        facility.setManager(null);

        System.out.println("Facility " + facilityId + " no longer has manager.");
    }

    @Transactional
    public List<Worker> getFacilityWorkers(int facilityId)
    {
        var facility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new EntityNotFoundException("Facility of id: "+ facilityId +" not found."));

        // Force initialization
        facility.getWorkers().size();

        return facility.getWorkers();
    }
}
