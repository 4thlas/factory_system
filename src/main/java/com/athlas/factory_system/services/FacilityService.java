package com.athlas.factory_system.services;

import com.athlas.factory_system.entities.Facility;
import com.athlas.factory_system.entities.Worker;
import com.athlas.factory_system.repositories.FacilityRepository;
import com.athlas.factory_system.repositories.ProductTypeRepository;
import com.athlas.factory_system.repositories.WorkerRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

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



}
