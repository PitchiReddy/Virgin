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
import com.virginvoyages.dao.ReferencesDAO;
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
	private ReferencesDAO referencesDao;
	
	
	@Autowired
	private ReferenceRepository referenceRepository;
	

	/**
	 * Create reference based on reference. Dummy data being used as of now
	 * - as data source not finalized
	 * 
	 * @param reference
	 *            - input reference.
	 * @return
	 */
	@Override
	public void addReference(Reference reference) {
		log.debug("adding references");
		//referencesDao.addReference(reference);
		referenceRepository.save(reference.convertToDataEntity());
	}

	/**
	 * Find reference by ID. Dummy data being used as of now - as data source
	 * not finalized
	 * 
	 * @param referenceID
	 *            - input reference.
	 * @return Reference - returns a reference
	 */
	public Reference findReferenceByID(String referenceID) {
		return	referencesDao.findReferenceByID(referenceID);
	}

	/**
	 * Find reference by ID. Dummy data being used as of now - as data source
	 * not finalized
	 * 
	 * @param referenceID
	 *            - input reference.
	 * @return 
	 */
	@Override
	public void deleteReferenceByID(String referenceID) {
		log.debug("Entering deleteReferenceByID method in ReferencesAssemblyImpl");
		referencesDao.deleteReferenceByID(referenceID);
		
	}
	/**
	 * Finding reference Type. Dummy data being used as of now - as data source not
	 * finalized
	 * 
	 * @return List Of References
	 */
	@Override
	public References findReferences(Integer page, Integer size) {
		log.debug("Entering findReferencesByMaster method in ReferencesAssemblyImpl");
		return referencesDao.findReferences(page,size);
	}
	
	/**
	 * Finding reference. Dummy data being used as of now - as data source not
	 * finalized
	 * 
	 * @return List Of Reference
	 */
	@Override
	public List<Reference> findReferencesByMaster(String masterID) {
		log.debug("Entering findReferencesByMaster method in ReferencesAssemblyImpl");
		return referencesDao.findReferencesByMaster(masterID);
	}
	
	/**
	 * Update reference Type by ID. Dummy data being used as of now - as data source
	 * not finalized
	 * 
	 * @param referenceID
	 * @param reference
	 * @return 
	 */
	@Override
	public void updateReference(String referenceID, Reference reference) {
		log.debug("Entering deleteReferenceByID method in ReferencesAssemblyImpl");
		referencesDao.updateReference(referenceID, reference);
	}

}
