package com.athlas.factory_system.services;

import com.athlas.factory_system.entities.ProductType;
import com.athlas.factory_system.repositories.ProductTypeRepository;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.transaction.Transactional;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ProductsService
{
    private final ProductTypeRepository productTypeRepository;

    @Transactional
    public void addProductType(String name, BigDecimal price)
    {
        var newProductType = ProductType.builder()
                .name(name)
                .price(price)
                .build();

        productTypeRepository.save(newProductType);
    }
}
