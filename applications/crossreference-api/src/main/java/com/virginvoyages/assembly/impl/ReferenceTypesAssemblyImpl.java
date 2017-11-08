package com.virginvoyages.assembly.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Service;

import com.virginvoyages.assembly.ReferenceTypesAssembly;
import com.virginvoyages.crossreference.exceptions.DataAccessException;
import com.virginvoyages.crossreference.exceptions.DataInsertionException;
import com.virginvoyages.crossreference.exceptions.DataNotFoundException;
import com.virginvoyages.crossreference.exceptions.DataUpdationException;
import com.virginvoyages.crossreference.exceptions.UnknownException;
import com.virginvoyages.crossreference.types.ReferenceType;
import com.virginvoyages.data.entities.ReferenceTypeData;
import com.virginvoyages.data.repositories.ReferenceTypeRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Implementation for {@link ReferenceTypesAssembly}
 * 
 * @author snarthu
 *
 */

@Service
@Slf4j
public class ReferenceTypesAssemblyImpl implements ReferenceTypesAssembly {

	@Autowired
	private ReferenceTypeRepository referenceTypeRepository;
	
	/**
	 * Create reference Type based on referenceType.
	 * @param referenceType
	 *            - input referenceType.
	 * @return
	 */

	@Override
	public ReferenceType addReferenceType(ReferenceType referenceType) {
		log.debug("Entering addReferenceType method in ReferenceTypesAssemblyImpl");
		referenceType.referenceTypeID(StringUtils.EMPTY);
		if(StringUtils.isEmpty(referenceType.referenceType())) {
			referenceType.referenceType(null);
		}
		try {
			ReferenceTypeData referenceTypeData = referenceTypeRepository.save(referenceType.convertToDataEntity());
			return (null == referenceTypeData || StringUtils.isBlank(referenceTypeData.referenceTypeID())) ? null : referenceTypeData.convertToBusinessEntity();
		}catch(JpaObjectRetrievalFailureException jex) {
			log.error("DataIntegrityViolationException encountered while adding reference type",jex);
			String errorMessage = null != jex.getRootCause() ? jex.getRootCause().getMessage():jex.getMessage();
			throw new DataInsertionException(errorMessage);
		}catch(DataIntegrityViolationException dex) {	
			log.error("DataIntegrityViolationException encountered while adding reference type",dex);
			String errorMessage = null != dex.getRootCause() ? dex.getRootCause().getMessage():dex.getMessage();
			throw new DataInsertionException(errorMessage);
		}catch(Exception ex) {
			log.error("Exception encountered while adding reference type",ex);
			throw new UnknownException();
		}
	}

	/**
	 * Find reference Type by ID. 
	 * @param referenceTypeID
	 *            - input referenceType.
	 * @return ReferenceType - returns a referenceType
	 */

	@Override
	public ReferenceType findReferenceTypeByID(String referenceTypeID) {
		log.debug("Entering findReferenceTypeByID method in ReferenceTypesAssemblyImpl");
		ReferenceTypeData referenceTypeData = referenceTypeRepository.findOne(referenceTypeID);
		if(referenceTypeData==null) {
		throw new DataNotFoundException();
		}
		return referenceTypeData.convertToBusinessEntity();

	}

	/**
	 * Delete reference Type by ID. 
	 * @param referenceTypeID
	 *            - input referenceType.
	 * @return
	 */
	public void deleteReferenceTypeByID(String referenceTypeID) {
		log.debug("Entering deleteReferenceTypeByID method in ReferenceTypesAssemblyImpl");
		try {
			referenceTypeRepository.delete(referenceTypeID);
		}
		catch(EmptyResultDataAccessException erdae) {
			throw new DataNotFoundException();
		}
		catch(DataIntegrityViolationException die) {
			throw new DataAccessException();
		}

	}

	/**
	 * Update reference Type by ID. 
	 * @param referenceType
	 * @param referenceTypeID
	 * @return
	 */
	@Override
	public ReferenceType updateReferenceType(ReferenceType referenceType) {
		log.debug("Entering updateReferenceType method in ReferenceTypesAssemblyImpl");
		ReferenceTypeData referenceTypeData = null;
		ReferenceTypeData findReferenceTypeData = referenceTypeRepository.findOne(referenceType.referenceTypeID());
		if(null!=findReferenceTypeData) {
			referenceTypeData = referenceTypeRepository.save(referenceType.convertToDataEntity());
		}
		else {
			throw new DataUpdationException();
		}
		return referenceTypeData.convertToBusinessEntity();
	}

	/**
	 * Finding reference Type.  
	 * @return
	 */
	@Override
	public List<ReferenceType> findTypes() {
		log.debug("Entering findTypes method in ReferenceTypesAssemblyImpl");
		List<ReferenceTypeData> listOfReferenceTypeData = (List<ReferenceTypeData>)referenceTypeRepository.findAll();
		List<ReferenceType> listOfReferenceType = new ArrayList<>();
		if(null != listOfReferenceTypeData && listOfReferenceTypeData.size() > 0 ) {
			listOfReferenceType = listOfReferenceTypeData.stream().map(referenceTypeData -> referenceTypeData.convertToBusinessEntity()).collect(Collectors.toList());
		}
		return listOfReferenceType;
	}

}