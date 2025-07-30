package com.athlas.factory_system.services;

import com.athlas.factory_system.entities.ProductType;
import com.athlas.factory_system.exceptions.productExcepions.ProductTypeAlreadyExists;
import com.athlas.factory_system.exceptions.productExcepions.ProductTypeInUse;
import com.athlas.factory_system.repositories.FacilityRepository;
import com.athlas.factory_system.repositories.ProductTypeRepository;
import jakarta.transaction.Transactional;
import lombok.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductsService
{
    private final ProductTypeRepository productTypeRepository;
    private final FacilityRepository facilityRepository;

    @Transactional
    public void addProductType(String name, BigDecimal price)
    {
        // Check if given product name exists
        if (productTypeRepository.findByName(name) == null)
        {
            var newProductType = ProductType.builder()
                    .name(name)
                    .price(price)
                    .build();

            productTypeRepository.save(newProductType);
        }
        else {
            throw new ProductTypeAlreadyExists("Product type \""+ name +"\" already exists");
        }
    }

    @Transactional
    public void removeProductType(String name)
    {
        var productType = productTypeRepository.findByNameIgnoreCase(name);

        var facilitiesUsingType = facilityRepository.findAllByProductType(productType);

        // Check if any facility manufactures this type
        if (facilitiesUsingType.isEmpty())
        {
            productTypeRepository.deleteById(productType.getId());
        }
        else
        {
            throw new ProductTypeInUse("Product type \""+ name +"\" is in use by facilities of id's: "+
                    facilitiesUsingType.stream()
                            .map(facility -> String.valueOf(facility.getId()))
                            .collect(Collectors.joining(", ")));
        }
    }
}
