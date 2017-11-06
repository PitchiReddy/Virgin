package com.virginvoyages.data.repositories;

import org.springframework.data.repository.CrudRepository;
import com.virginvoyages.data.entities.ReferenceData;

public interface ReferenceRepository extends CrudRepository<ReferenceData, String> {

}