/**
 * 
 */
package com.virginvoyages.assembly.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.virginvoyages.assembly.ReferencesAssembly;
import com.virginvoyages.crossreference.references.Reference;
import com.virginvoyages.crossreference.references.References;
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
	 * - as data source not finalized
	 * 
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
		return null;
		
	}

	/**
	 * Find reference by ID. 
	 * not finalized
	 * 
	 * @param referenceID
	 *            - input reference.
	 * @return 
	 */
	@Override
	public void deleteReferenceByID(String referenceID) {
		log.debug("Entering deleteReferenceByID method in ReferencesAssemblyImpl");
		
		
	}
	/**
	 * Finding reference Type.
	 * finalized
	 * 
	 * @return List Of References
	 */
	@Override
	public References findReferences(Integer page, Integer size) {
		log.debug("Entering findReferencesByMaster method in ReferencesAssemblyImpl");
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
		return null;
	}
	
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
