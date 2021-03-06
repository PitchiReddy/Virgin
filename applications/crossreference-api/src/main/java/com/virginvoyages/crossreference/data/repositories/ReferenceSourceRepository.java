package com.virginvoyages.crossreference.data.repositories;

import java.io.Serializable;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.virginvoyages.crossreference.data.entities.ReferenceSourceData;

/**
 * {@code Interface} for ReferenceSourceRepository operations for ReferenceSource
 * @author snarthu
 *
 */
@Repository
public interface ReferenceSourceRepository extends PagingAndSortingRepository<ReferenceSourceData, Serializable> {

	/**
	 * Name based query to get ReferenceType based on name
	 * @param referenceTypeName
	 * @return
	 */
	ReferenceSourceData findByReferenceSource(String referenceSourceName);
} 