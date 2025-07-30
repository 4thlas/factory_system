package com.athlas.factory_system.services;

import com.athlas.factory_system.entities.ProductionLine;
import org.springframework.data.repository.CrudRepository;

public interface ProductionLineRepository extends CrudRepository<ProductionLine, Integer> {
}