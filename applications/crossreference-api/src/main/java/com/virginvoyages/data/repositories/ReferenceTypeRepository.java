package com.virginvoyages.data.repositories;

import org.springframework.data.repository.CrudRepository;

import com.virginvoyages.data.entities.ReferenceTypeData;

/**
 * {@code Interface} for ReferenceTypeRepository operations for ReferenceType
 * @author snarthu
 *
 */
public interface ReferenceTypeRepository extends CrudRepository<ReferenceTypeData, String> {
	
}