package com.athlas.factory_system.repositories;

import com.athlas.factory_system.entities.Facility;
import com.athlas.factory_system.entities.Worker;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FacilityRepository extends CrudRepository<Facility, Integer> {
    List<Facility> findAllByManager(Worker manager);
}
