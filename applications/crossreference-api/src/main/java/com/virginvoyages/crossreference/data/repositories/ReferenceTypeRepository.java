package com.virginvoyages.crossreference.data.repositories;

import org.springframework.stereotype.Repository;
import java.io.Serializable;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.virginvoyages.crossreference.data.entities.ReferenceTypeData;

/**
 * {@code Interface} for ReferenceTypeRepository operations for ReferenceType
 * @author snarthu
 *
 */
@Repository
public interface ReferenceTypeRepository extends PagingAndSortingRepository<ReferenceTypeData, Serializable> {

	/**
	 * Name based query to get ReferenceType based on name
	 * @param referenceTypeName
	 * @return
	 */
	ReferenceTypeData findByReferenceType(String referenceTypeName);
	
}