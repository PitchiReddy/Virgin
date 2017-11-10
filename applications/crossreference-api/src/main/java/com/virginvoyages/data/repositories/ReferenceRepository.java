package com.virginvoyages.data.repositories;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.virginvoyages.crossreference.data.entities.ReferenceData;


@Repository
public interface ReferenceRepository extends PagingAndSortingRepository<ReferenceData, Serializable>{

	Page<ReferenceData> findByMasterID(String masterID, Pageable pageable);
	
	
}
