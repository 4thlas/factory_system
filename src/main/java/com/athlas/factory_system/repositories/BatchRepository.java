package com.athlas.factory_system.repositories;

import com.athlas.factory_system.entities.Batch;
import com.athlas.factory_system.entities.ProductionLine;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.util.List;

public interface BatchRepository extends CrudRepository<Batch, BigDecimal>
{
    List<Batch> findAllByProductionLine(ProductionLine productionLine);
}
