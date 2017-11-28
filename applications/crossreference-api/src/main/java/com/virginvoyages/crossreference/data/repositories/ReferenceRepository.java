package com.virginvoyages.crossreference.data.repositories;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.virginvoyages.crossreference.data.entities.ReferenceData;

@Repository
public interface ReferenceRepository extends PagingAndSortingRepository<ReferenceData, Serializable>{

	List<ReferenceData> findByMasterID(String masterID);
	
	List<ReferenceData> findByMasterIDAndReferenceTypeDataReferenceTypeID(@Param("masterID") String masterID, @Param("targetTypeID") String targetTypeID);
	
} 