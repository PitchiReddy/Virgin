package com.virginvoyages.crossreference.data.repositories;

import org.springframework.data.repository.CrudRepository;
import com.virginvoyages.crossreference.data.entities.ReferenceData;


public interface ReferenceRepository extends CrudRepository<ReferenceData, String> {

}