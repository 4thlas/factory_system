package com.athlas.factory_system.repositories;

import com.athlas.factory_system.entities.ManufacturedProduct;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;

public interface ManufacturedProductRepository extends CrudRepository<ManufacturedProduct, BigDecimal> {
}
