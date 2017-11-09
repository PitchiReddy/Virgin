package com.virginvoyages.crossreference.data.repositories;

import org.springframework.data.repository.CrudRepository;

import com.virginvoyages.crossreference.data.entities.ReferenceSourceData;

/**
 * {@code Interface} for ReferenceSourceRepository operations for ReferenceSource
 * @author snarthu
 *
 */
public interface ReferenceSourceRepository extends CrudRepository<ReferenceSourceData, String> {
	
} 