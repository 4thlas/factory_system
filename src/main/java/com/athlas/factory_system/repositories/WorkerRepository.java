package com.athlas.factory_system.repositories;

import com.athlas.factory_system.entities.Worker;
import org.springframework.data.repository.CrudRepository;

public interface WorkerRepository extends CrudRepository<Worker, Integer> {
}
