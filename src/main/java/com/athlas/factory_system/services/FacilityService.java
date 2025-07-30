package com.athlas.factory_system.services;

import com.athlas.factory_system.entities.Facility;
import com.athlas.factory_system.exceptions.productExcepions.ProductTypeInUse;
import com.athlas.factory_system.repositories.FacilityRepository;
import com.athlas.factory_system.repositories.ProductTypeRepository;
import com.athlas.factory_system.repositories.WorkerRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@Setter
@Getter
@AllArgsConstructor
public class FacilityService
{
    private final FacilityRepository facilityRepository;
    private final WorkerRepository workerRepository;
    private final ProductTypeRepository productTypeRepository;

    @Transactional
    public void createFacility(String location, int productTypeId)
    {
        var productType = productTypeRepository.findById(productTypeId).orElseThrow();

        var newFacility = Facility.builder()
                .location(location)
                .productType(productType)
                .build();

        facilityRepository.save(newFacility);
    }

    @Transactional
    public void removeFacility(int facilityId)
    {
        var facility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new EntityNotFoundException("Facility of id: "+ facilityId + " not found."));

        // Check if the facility has workers assigned
        if (facility.getWorkers().isEmpty())
        {
            facilityRepository.deleteById(facilityId);
        }
        else
        {
            var facilityWorkers = facility.getWorkers();

            throw new ProductTypeInUse("Facility of id: "+ facilityId +" has assigned workers od id's: "+
                    facilityWorkers.stream()
                            .map(worker -> String.valueOf(worker.getId()))
                            .collect(Collectors.joining(", ")));
        }
    }

    @Transactional
    public void setProductType(int facilityId, String productTypeName)
    {
        var facility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new EntityNotFoundException("Facility of id: "+ facilityId + " not found."));

        var productType = productTypeRepository.findByNameIgnoreCase(productTypeName);

        facility.setProductType(productType);

        System.out.println("Facility " + facilityId + " is now manufacturing " + productType.getName());
    }
}
