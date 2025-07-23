package com.athlas.factory_system.services;

import com.athlas.factory_system.entities.Worker;
import com.athlas.factory_system.repositories.FacilityRepository;
import com.athlas.factory_system.repositories.WorkerRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;

@Service
@Setter
@Getter
@AllArgsConstructor
public class PersonelService
{
    private final FacilityService facilityService;
    private final FacilityRepository facilityRepository;
    private final WorkerRepository workerRepository;

    @Transactional
    public void addWorker(int facilityId, String name, String role, int paycheck)
    {
        var assignedFacility = facilityRepository.findById(facilityId).orElseThrow();
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
        var worker = workerRepository.findById(workerId).orElseThrow();

        if (worker.getRole().equals("manager"))
        {
            unassignManager(worker.getFacility().getId());
        }

        workerRepository.delete(worker);
    }

    @Transactional
    public void moveWorker(int workerId, int targetFacilityId) //TODO
    {
        var worker = workerRepository.findById(workerId).orElseThrow();
        var targetFacility = facilityRepository.findById(targetFacilityId).orElseThrow();

        if (worker.getRole().equals("manager"))
        {
            unassignManager(worker.getFacility().getId());
        }

        worker.setFacility(targetFacility);

        worker.getFacility().getWorkers().remove(worker);
        targetFacility.getWorkers().add(worker);
    }

    @Transactional
    public void assignManager(int workerId, int facilityId) throws IOException {
        var worker = workerRepository.findById(workerId).orElseThrow();

        if (worker.getFacility().getId() == facilityId)
        {
            var facility = facilityRepository.findById(facilityId).orElseThrow();

            if (facility.getManager() == null)
            {
                facility.setManager(worker);
            }
            else throw new IOException("Facility " + facility.getId() + " already has a manager (" + facility.getManager().getId() + ")");

        }
        else throw new IOException("Worker " + worker.getName() + " of id " +  workerId + " isn't assigned to facility " + facilityId);
    }

    @Transactional
    public void unassignManager(int facilityId)
    {
        var facility = facilityRepository.findById(facilityId).orElseThrow();
        facility.setManager(null);
        System.out.println("Facility " + facilityId + " no longer has manager.");
    }
}
