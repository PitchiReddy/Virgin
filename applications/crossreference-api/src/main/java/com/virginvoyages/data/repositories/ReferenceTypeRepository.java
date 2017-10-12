package com.virginvoyages.data.repositories;

import org.springframework.data.repository.CrudRepository;

import com.virginvoyages.data.entities.ReferenceTypeData;

public interface ReferenceTypeRepository extends CrudRepository<ReferenceTypeData, String> {
	
}