/**
 * 
 */
package com.virginvoyages.assembly.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.virginvoyages.assembly.ReferencesAssembly;
import com.virginvoyages.crossreference.exceptions.DataNotFoundException;
import com.virginvoyages.crossreference.references.Reference;
import com.virginvoyages.data.entities.ReferenceData;
import com.virginvoyages.data.repositories.ReferenceRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Implementation for {@link ReferencesAssembly}
 * @author snarthu
 *
 */
@Service
@Slf4j
public class ReferencesAssemblyImpl implements ReferencesAssembly {

	@Autowired
	private ReferenceRepository referenceRepository;
	

	/**
	 * Create reference based on reference. 
	 * @param reference
	 *            - input reference.
	 * @return Reference - returns a reference
	 */
	@Override
	public Reference addReference(Reference reference) {
		log.debug("Entering addReference method in ReferencesAssemblyImpl");
		ReferenceData referenceData =	referenceRepository.save(reference.convertToDataEntity());
		return referenceData.convertToBusinessEntity();
	}

	/**
	 * Find reference by ID. 
	 * @param referenceID
	 *            - input referenceID.
	 * @return Reference - returns a reference
	 */
	public Reference findReferenceByID(String referenceID) {
		log.debug("Entering findReferenceByID method in ReferencesAssemblyImpl");
		ReferenceData referenceData = referenceRepository.findOne(referenceID);
		if(referenceData == null) {
			throw new DataNotFoundException();
		}
		return referenceData.convertToBusinessEntity();
	}

	/**
	 * delete reference by ID. 
	 * @param referenceID
	 *            - input referenceID.
	 * @return 
	 */
	@Override
	public void deleteReferenceByID(String referenceID) {
		log.debug("Entering deleteReferenceByID method in ReferencesAssemblyImpl");
		referenceRepository.delete(referenceID);
		
		
	}
	/**
	 * Finding references
	 * @return List Of Reference
	 */
	@Override
	public List<Reference> findReferences() {
		log.debug("Entering findReferences method in ReferencesAssemblyImpl");
		List<ReferenceData> listOfReferenceData = (List<ReferenceData>)referenceRepository.findAll();
		List<Reference> listOfReference = new ArrayList<>();
		if(null != listOfReferenceData && listOfReferenceData.size() > 0 ) {
			listOfReference = listOfReferenceData.stream().map(referenceData->referenceData.convertToBusinessEntity()).collect(Collectors.toList());
		}
		return listOfReference;
		
	}
	
	/**
	 * Finding reference. 
	@Override
	 * @return List Of Reference
	 */
	public List<Reference> findReferencesByMaster(String masterID) {
		log.debug("Entering findReferencesByMaster method in ReferencesAssemblyImpl");
		Iterable<ReferenceData> referenceDataIterable = referenceRepository.findAll();
		List<Reference> listOfReference = new ArrayList<>();
		for (ReferenceData referenceData : referenceDataIterable) {
			if((referenceData.masterID()).equals(masterID)) {
			listOfReference.add(referenceData.convertToBusinessEntity());
			}
		}
		return null == referenceDataIterable ? null : listOfReference;	
	}
	/**
	 * Update reference Type by ID. 
	 * @param referenceID
	 * @param reference
	 * @return 
	 */
	@Override
	public Reference updateReference(Reference reference) {
		log.debug("Entering deleteReferenceByID method in ReferencesAssemblyImpl");
		ReferenceData referenceData =	referenceRepository.save(reference.convertToDataEntity());
		return referenceData.convertToBusinessEntity();
		
	}

	@Override
	public List<Reference> findReferencesBySource(Reference reference) {
		log.debug("Entering findReferencesBySource method in ReferencesAssemblyImpl");
		return null;
	}

	@Override
	public List<Reference> findReferencesSourceAndTargetSource(Reference reference) {
		log.debug("Entering findReferencesSourceAndTargetSource method in ReferencesAssemblyImpl");
		return null;
	
	}

}
