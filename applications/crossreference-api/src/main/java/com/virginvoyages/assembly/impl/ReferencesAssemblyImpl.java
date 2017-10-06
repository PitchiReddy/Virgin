/**
 * 
 */
package com.virginvoyages.assembly.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.virginvoyages.assembly.ReferencesAssembly;
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
	 * @return
	 */
	@Override
	public void addReference(Reference reference) {
		log.debug("adding references");
		referenceRepository.save(reference.convertToDataEntity());
	}

	/**
	 * Find reference by ID. 
	 * @param referenceID
	 *            - input reference.
	 * @return Reference - returns a reference
	 */
	public Reference findReferenceByID(String referenceID) {
		log.debug("Entering findReferenceByID method in ReferencesAssemblyImpl");
		ReferenceData referenceData = referenceRepository.findOne(Long.valueOf(referenceID));
		return null == referenceData ? null : referenceData.convertToBusinessEntity();
		
	}

	/**
	 * Find reference by ID. 
	 * @param referenceID
	 *            - input reference.
	 * @return 
	 */
	@Override
	public void deleteReferenceByID(String referenceID) {
		log.debug("Entering deleteReferenceByID method in ReferencesAssemblyImpl");
		long convertReferenceID = Long.parseLong(referenceID);
		referenceRepository.delete(convertReferenceID);
		
		
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
	 * finalized
	 * 
	 * @return List Of Reference
	 */
	@Override
	public List<Reference> findReferencesByMaster(String masterID) {
		log.debug("Entering findReferencesByMaster method in ReferencesAssemblyImpl");
		Iterable<ReferenceData> referenceDataIterable = referenceRepository.findAll();
		List<Reference> listOfReference = new ArrayList<>();
		for (ReferenceData referenceData : referenceDataIterable) {
			listOfReference.add(referenceData.convertToBusinessEntity());
		}
		return null == referenceDataIterable ? null : listOfReference;	}
	
	/**
	 * Update reference Type by ID. 
	 * not finalized
	 * 
	 * @param referenceID
	 * @param reference
	 * @return 
	 */
	@Override
	public void updateReference(String referenceID, Reference reference) {
		log.debug("Entering deleteReferenceByID method in ReferencesAssemblyImpl");
		
	}

}
