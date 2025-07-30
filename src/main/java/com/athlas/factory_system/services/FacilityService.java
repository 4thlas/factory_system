package com.athlas.factory_system.services;

import com.athlas.factory_system.entities.Facility;
import com.athlas.factory_system.entities.ProductionLine;
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

import java.math.BigDecimal;
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
    private final ProductionLineRepository productionLineRepository;

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
            // Delete assigned production lines
            facility.getProductionLines().forEach(productionLine -> removeProductionLine(productionLine.getId()));

            facilityRepository.deleteById(facilityId);

            System.out.println("Facility "+ facilityId +" has been deleted.");
        }
        else
        {
            var facilityWorkers = facility.getWorkers();

            throw new ProductTypeInUse("Facility of id: "+ facilityId +" has assigned workers of id's: "+
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

    @Transactional
    public void addProductionLine(int facilityId)
    {
        var facility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new EntityNotFoundException("Facility of id: "+ facilityId + " not found."));

        // Add new production line to DB
        var newProductionLine = ProductionLine.builder()
                .facility(facility)
                .producedPerMonth(BigDecimal.valueOf(0))
                .build();
        productionLineRepository.save(newProductionLine);

        System.out.println("Production line "+ newProductionLine.getId() +" has been created in facility "+ facilityId);
    }

    @Transactional
    public void removeProductionLine(int id)
    {
        var productionLine = productionLineRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Production line of id: "+ id +" not found."));

        int facilityId = productionLine.getFacility().getId();

        productionLineRepository.delete(productionLine);

        System.out.println("Production line "+ id +" deleted from facility "+ facilityId);
    }
}
