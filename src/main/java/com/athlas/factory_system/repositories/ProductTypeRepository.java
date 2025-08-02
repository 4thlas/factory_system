package com.athlas.factory_system.repositories;

import com.athlas.factory_system.entities.ProductType;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductTypeRepository extends CrudRepository<ProductType, Integer>
{
    Optional<ProductType> findByName(String name);

    Optional<ProductType> findByNameIgnoreCase(String name);
}
