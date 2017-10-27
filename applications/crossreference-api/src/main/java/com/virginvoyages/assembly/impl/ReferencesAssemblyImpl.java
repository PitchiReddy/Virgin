/**
 * 
 */
package com.virginvoyages.assembly.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.virginvoyages.assembly.ReferencesAssembly;
import com.virginvoyages.crossreference.exceptions.DataNotFoundException;
import com.virginvoyages.crossreference.exceptions.DataUpdationException;
import com.virginvoyages.crossreference.references.Reference;
import com.virginvoyages.crossreference.references.References;
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
		return null == referenceData ? null : referenceData.convertToBusinessEntity();
		
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
		try{
			referenceRepository.delete(referenceID);
		}
		catch(EmptyResultDataAccessException | DataIntegrityViolationException dive) {
			throw new DataNotFoundException();
		}
	
	}
	/**
	 * Finding reference Type.
	 * @return List Of References
	 */
	@Override
	public References findReferences() {
		log.debug("Entering findReferences method in ReferencesAssemblyImpl");
		return null;
		
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
		ReferenceData referenceData = null;
		ReferenceData	findReferenceData = referenceRepository.findOne(reference.referenceID());
		if(null == findReferenceData) {
			throw new DataUpdationException();
		}
		else {
		 referenceData = referenceRepository.save(reference.convertToUpdateDataEntity(reference.referenceID()));
		}
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
