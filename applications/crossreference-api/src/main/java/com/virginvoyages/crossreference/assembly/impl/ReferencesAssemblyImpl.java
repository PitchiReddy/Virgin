/**
 * 
 */
package com.virginvoyages.crossreference.assembly.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.virginvoyages.crossreference.assembly.ReferencesAssembly;
import com.virginvoyages.crossreference.data.entities.ReferenceData;
import com.virginvoyages.crossreference.data.repositories.ReferenceRepository;
import com.virginvoyages.exceptions.DataAccessException;
import com.virginvoyages.exceptions.DataInsertionException;
import com.virginvoyages.exceptions.DataNotFoundException;
import com.virginvoyages.exceptions.DataUpdationException;
import com.virginvoyages.exceptions.UnknownException;
import com.virginvoyages.model.crossreference.Reference;

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
		log.debug("Entering addReference method in ReferencesAssemblyImpl.reference.nativeSourceIDValue() ==> "+reference.nativeSourceIDValue());
		reference.referenceID(StringUtils.EMPTY);
		if(StringUtils.isEmpty(reference.nativeSourceIDValue())) {
			reference.nativeSourceIDValue(null);
		}
		try {
			ReferenceData referenceData	= referenceRepository.save(reference.convertToDataEntity());
			return (null == referenceData || StringUtils.isBlank(referenceData.referenceID())) ? null : referenceData.convertToBusinessEntity();
		}catch(DataIntegrityViolationException dex) {	
			log.error("DataIntegrityViolationException encountered while adding reference ",dex);
			String errorMessage = null != dex.getRootCause() ? dex.getRootCause().getMessage():dex.getMessage();
			throw new DataInsertionException(errorMessage);
		}catch(Exception ex) {
			log.error("Exception encountered while adding reference ",ex);
			throw new UnknownException();
		}
	}

	/**
	 * Find reference by ID. 
	 * @param referenceID
	 *            - input referenceID.
	 * @return Reference - returns a reference
	 */
	public Reference findReferenceByID(String referenceID) {
		log.debug("Entering findReferenceByID method in ReferencesAssemblyImpl for referenceID ==> " + referenceID);
		try {
			ReferenceData referenceData = referenceRepository.findOne(referenceID);
			return null == referenceData ? null : referenceData.convertToBusinessEntity();
		} catch (Exception ex) {
			log.error("Find Reference ID ==>" + referenceID + "\nException encountered in findReferenceByID", ex);
			throw new UnknownException();
		}
	}

	/**
	 * delete reference by ID. 
	 * @param referenceID
	 *            - input referenceID.
	 * @return 
	 */
	@Override
	public void deleteReferenceByID(String referenceID) {
		log.debug("Entering deleteReferenceByID method in ReferencesAssemblyImpl for referenceID ==> "+referenceID);
		try{
			referenceRepository.delete(referenceID);
		}catch(EmptyResultDataAccessException edex) {
			log.error("Reference ID ==>"+referenceID+"\nEmptyResultDataAccessException encountered in deleteReferenceByID",edex);
			throw new DataNotFoundException();
		}catch(DataIntegrityViolationException dex) {
			log.error("Reference ID ==>"+referenceID+"\nDataIntegrityViolationException encountered in deleteReferenceByID",dex);
			throw new DataAccessException();
		}catch(Exception ex) {
			log.error("Reference ID ==>"+referenceID+"\nException encountered in deleteReferenceByID",ex);
			throw new UnknownException();
		}
		log.debug("Exiting deleteReferenceByID method in ReferencesAssemblyImpl");
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
	 * Update reference by ID. 
	 * @param reference
	 * @return 
	 */
	@Override
	public Reference updateReference(Reference reference) {
		log.debug("Entering updateReference method in ReferencesAssemblyImpl");
		if(!referenceRepository.exists(reference.referenceID())){
			log.error("Reference does not exist with ID ==> "+reference.referenceID());
			throw new DataUpdationException();
		}
		try {
			ReferenceData referenceData = referenceRepository.save(reference.convertToDataEntity());
			return (null == referenceData || StringUtils.isBlank(referenceData.referenceID())) ? null : referenceData.convertToBusinessEntity();
		}catch(DataIntegrityViolationException dex) {	
			log.error("DataIntegrityViolationException encountered while updating reference ",dex);
			throw new DataUpdationException();
		}catch(Exception ex) {
			log.error("Exception encountered while updating reference ",ex);
			throw new UnknownException();
		}
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
