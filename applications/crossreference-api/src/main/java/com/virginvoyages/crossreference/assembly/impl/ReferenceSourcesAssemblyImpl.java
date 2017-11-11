package com.virginvoyages.crossreference.assembly.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.virginvoyages.crossreference.assembly.ReferenceSourcesAssembly;
import com.virginvoyages.crossreference.data.entities.ReferenceSourceData;
import com.virginvoyages.crossreference.data.repositories.ReferenceSourceRepository;
import com.virginvoyages.model.crossreference.ReferenceSource;
import com.virginvoyages.exceptions.DataAccessException;
import com.virginvoyages.exceptions.DataInsertionException;
import com.virginvoyages.exceptions.DataNotFoundException;
import com.virginvoyages.exceptions.DataUpdationException;
import com.virginvoyages.exceptions.UnknownException;

import lombok.extern.slf4j.Slf4j;

/**
 * Implementation for {@link ReferenceSourcesAssemblyImpl}
 * 
 * @author pbovilla
 */
@Service
@Slf4j
public class ReferenceSourcesAssemblyImpl implements ReferenceSourcesAssembly {

	@Autowired
	private ReferenceSourceRepository referenceSourceRepository;

	/**
	 * Create reference source based on referenceSource.
	 * 
	 * @param referenceSource
	 *            - input referenceSource.
	 * @return
	 */
	@Override
	public ReferenceSource addReferenceSource(ReferenceSource referenceSource) {
		log.debug("Entering addReferenceSource method in ReferenceSourcesAssemblyImpl. referenceSource.referenceSource() ==> "+referenceSource.referenceSource());
		referenceSource.referenceSourceID(StringUtils.EMPTY);
		if(StringUtils.isEmpty(referenceSource.referenceSource())) {
			referenceSource.referenceSource(null);
		}
		try {
			ReferenceSourceData referenceSourceData	= referenceSourceRepository.save(referenceSource.convertToDataEntity());
			return (null == referenceSourceData || StringUtils.isBlank(referenceSourceData.referenceSourceID())) ? null : referenceSourceData.convertToBusinessEntity();
		}catch(DataIntegrityViolationException dex) {	
			log.error("DataIntegrityViolationException encountered while adding reference source",dex);
			String errorMessage = null != dex.getRootCause() ? dex.getRootCause().getMessage():dex.getMessage();
			throw new DataInsertionException(errorMessage);
		}catch(Exception ex) {
			log.error("Exception encountered while adding reference source",ex);
			throw new UnknownException();
		}
	}

	/**
	 * Find reference source by ID.
	 * @param referenceSourceID
	 *            - input referenceSourceID.
	 * @return ReferenceSource - returns a ReferenceSource
	 */
	@Override
	public ReferenceSource findReferenceSourceByID(String referenceSourceID) {
		log.debug("Entering findReferenceSourceByID method in ReferenceSourcesAssemblyImpl for referenceSourceID ==> "+referenceSourceID);
		try {
			ReferenceSourceData referenceSourceData = referenceSourceRepository.findOne(referenceSourceID);
			return null == referenceSourceData ? null : referenceSourceData.convertToBusinessEntity();
		}catch(Exception ex) {
			log.error("Reference Source ID ==>"+referenceSourceID+"\nException encountered in findReferenceSourceByID",ex);
			throw new UnknownException();
		}
	}
	
	@Override
	public ReferenceSource findReferenceSourceByName(String referenceSourceName) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Remove the ReferenceSource.
	 * @param referenceSourceID
	 *            - input referenceSourceID.
	 * @return
	 */
	@Override
	public boolean deleteReferenceSourceByID(String referenceSourceID) {
		log.debug("Entering deleteReferenceSourceByID method in ReferenceSourcesAssemblyImpl for referenceSourceID ==> "+referenceSourceID);
		boolean deleted = false;
		try{
			referenceSourceRepository.delete(referenceSourceID);
			deleted = true;
		}catch(EmptyResultDataAccessException edex) {
			//When trying to delete source that does not exist
			log.error("Reference Source ID ==>"+referenceSourceID+"\nEmptyResultDataAccessException encountered in deleteReferenceSourceByID",edex);
			throw new DataNotFoundException();
		}catch(DataIntegrityViolationException dex) {
			//When trying to delete source that is referenced by a type
			log.error("Reference Source ID ==>"+referenceSourceID+"\nDataIntegrityViolationException encountered in deleteReferenceSourceByID",dex);
			throw new DataAccessException();
		}catch(Exception ex) {
			log.error("Reference Source ID ==>"+referenceSourceID+"\nException encountered in deleteReferenceSourceByID",ex);
			throw new UnknownException();
		}
		log.debug("Exiting deleteReferenceSourceByID method in ReferenceSourcesAssemblyImpl");
		return deleted;
	}

	/**
	 * Update a `ReferenceSource` object.
	 * @param referenceSourceID
	 *            - input referenceSourceID.
	 * @param referenceSource
	 *            - input referenceSource.
	 * @return
	 */
	@Override
	public ReferenceSource updateReferenceSource(ReferenceSource referenceSource) {
		log.debug("Entering updateReferenceSource method in ReferenceSourcesAssemblyImpl");
		
		if(!referenceSourceRepository.exists(referenceSource.referenceSourceID())){
			log.error("Reference source does not exist with ID ==> "+referenceSource.referenceSourceID());
			throw new DataUpdationException();
		}
		try {
			ReferenceSourceData referenceSourceData = referenceSourceRepository.save(referenceSource.convertToDataEntity());
			return (null == referenceSourceData || StringUtils.isBlank(referenceSourceData.referenceSourceID())) ? null : referenceSourceData.convertToBusinessEntity();
		}catch(DataIntegrityViolationException dex) {	
			log.error("DataIntegrityViolationException encountered while updating reference source",dex);
			throw new DataUpdationException();
		}catch(Exception ex) {
			log.error("Exception encountered while updating reference source",ex);
			throw new UnknownException();
		}
	}

	/**
	 * Gets `Source` objects.
	 * 
	 * @param <T>
	 * @return List<ReferenceSource>
	 */
	@Override
	public List<ReferenceSource> findSources() {
		log.debug("Entering findSources method in ReferenceSourcesAssemblyImpl");
		List<ReferenceSourceData> listOfReferenceSourceData = (List<ReferenceSourceData>)referenceSourceRepository.findAll();
		List<ReferenceSource> listOfReferenceSource = new ArrayList<>();
		if(null != listOfReferenceSourceData && listOfReferenceSourceData.size() > 0 ) {
			listOfReferenceSource = listOfReferenceSourceData.stream().map(referenceSourceData -> referenceSourceData.convertToBusinessEntity()).collect(Collectors.toList());
		}
		return listOfReferenceSource;
		
	}

}
