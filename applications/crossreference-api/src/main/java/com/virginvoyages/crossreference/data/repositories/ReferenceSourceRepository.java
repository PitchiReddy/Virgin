package com.virginvoyages.crossreference.data.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.virginvoyages.crossreference.data.entities.ReferenceSourceData;

/**
 * {@code Interface} for ReferenceSourceRepository operations for ReferenceSource
 * @author snarthu
 *
 */
@Repository
public interface ReferenceSourceRepository extends CrudRepository<ReferenceSourceData, String> {

	ReferenceSourceData findByReferenceSource(String referenceSourceName);
	
	
} 