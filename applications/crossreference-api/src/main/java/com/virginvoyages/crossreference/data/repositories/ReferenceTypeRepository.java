package com.virginvoyages.crossreference.data.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.virginvoyages.crossreference.data.entities.ReferenceTypeData;

/**
 * {@code Interface} for ReferenceTypeRepository operations for ReferenceType
 * @author snarthu
 *
 */
@Repository
public interface ReferenceTypeRepository extends CrudRepository<ReferenceTypeData, String> {

	ReferenceTypeData findByReferenceType(String referenceTypeName);
	
}