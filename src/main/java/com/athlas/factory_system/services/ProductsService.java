package com.athlas.factory_system.services;

import com.athlas.factory_system.entities.Batch;
import com.athlas.factory_system.entities.ManufacturedProduct;
import com.athlas.factory_system.entities.ProductType;
import com.athlas.factory_system.utils.ExceptionMessageUtil;
import com.athlas.factory_system.exceptions.productExcepions.ProductTypeAlreadyExists;
import com.athlas.factory_system.exceptions.productExcepions.ProductTypeInUse;
import com.athlas.factory_system.exceptions.productExcepions.ProductTypeMismatch;
import com.athlas.factory_system.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductsService
{
    private final ProductTypeRepository productTypeRepository;
    private final FacilityRepository facilityRepository;
    private final ProductionLineRepository productionLineRepository;
    private final ManufacturedProductRepository manufacturedProductRepository;
    private final BatchRepository batchRepository;

    @Transactional
    public void addProductType(String name, BigDecimal price)
    {
        // Check if given product name exists
        if (productTypeRepository.findByName(name).isEmpty())
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
        var productTypeOptional = productTypeRepository.findByNameIgnoreCase(name);

        var productType = productTypeOptional
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessageUtil.notFoundMsg("Product type", name)));

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

    @Transactional
    public void produceBatch(String productTypeName, int productionLineId)
    {
        var productType = productTypeRepository.findByNameIgnoreCase(productTypeName)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessageUtil.notFoundMsg("Product type", productionLineId)));

        var productionLine = productionLineRepository.findById(productionLineId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessageUtil.notFoundMsg("Production line", productionLineId)));

        // Check if productType matches facility's productType
        if (productionLine.getFacility().getProductType() == productType)
        {
            // Create new newBatch
            var newBatch = Batch.builder()
                    .productType(productType)
                    .productionDate(LocalDate.now())
                    .productionLine(productionLine)
                    .build();

            int defective = (int)(Math.random() * 11); // Select random product to be defective (for testing)

            BigDecimal batchValue = BigDecimal.valueOf(0);

            // Create 10 products and add them to newBatch
            for(int i = 1; i <= 10; i++)
            {
                var newProduct = ManufacturedProduct.builder()
                        .batch(newBatch)
                        .defective(i == defective)
                        .build();
                newBatch.getProducts().add(newProduct);
            }

            batchRepository.save(newBatch);
        }
        else
        {
            throw new ProductTypeMismatch(ExceptionMessageUtil.entityMismatchMsg("ProductType: "+productTypeName, "facility's productType"));
        }


    }
}
