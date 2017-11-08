package com.virginvoyages.data.repositories;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.virginvoyages.data.entities.ReferenceData;

@Repository
public interface ReferenceRepository extends PagingAndSortingRepository<ReferenceData, Serializable>{

	Page<ReferenceData> findByMasterID(String masterID, Pageable pageable);
	
	
}
