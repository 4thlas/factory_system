package com.athlas.factory_system.repositories;

import com.athlas.factory_system.entities.ProductType;
import org.springframework.data.repository.CrudRepository;

public interface ProductTypeRepository extends CrudRepository<ProductType, Integer> {
}
