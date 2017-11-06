package com.virginvoyages.data.repositories;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.virginvoyages.data.entities.ReferenceData;

@Repository
public interface ReferenceRepository extends JpaRepository<ReferenceData, Serializable>{

	List<ReferenceData> findByMasterID(String masterID);
	
	
}
