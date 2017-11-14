package com.virginvoyages.crossreference.assembly.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Service;

import com.virginvoyages.crossreference.assembly.ReferenceTypesAssembly;
import com.virginvoyages.crossreference.data.entities.ReferenceTypeData;
import com.virginvoyages.crossreference.data.repositories.ReferenceTypeRepository;
import com.virginvoyages.exceptions.DataAccessException;
import com.virginvoyages.exceptions.DataInsertionException;
import com.virginvoyages.exceptions.DataNotFoundException;
import com.virginvoyages.exceptions.DataUpdationException;
import com.virginvoyages.exceptions.UnknownException;
import com.virginvoyages.model.crossreference.ReferenceType;

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
		try {
			ReferenceTypeData referenceTypeData = referenceTypeRepository.findOne(referenceTypeID);
			return null == referenceTypeData ? null : referenceTypeData.convertToBusinessEntity();
		}catch(Exception ex) {
			log.error("Reference Type ID ==>"+referenceTypeID+"\nException encountered in findReferenceTypeByID",ex);
			throw new UnknownException();
		}

	}

	@Override
	public ReferenceType findReferenceTypeByName(String referenceTypeName) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Delete reference Type by ID. 
	 * @param referenceTypeID
	 *            - input referenceType.
	 * @return
	 */
	public boolean deleteReferenceTypeByID(String referenceTypeID) {
		log.debug("Entering deleteReferenceTypeByID method in ReferenceTypesAssemblyImpl");
		boolean deleted = false;
		try {
			referenceTypeRepository.delete(referenceTypeID);
			deleted = true;
			
		}catch(EmptyResultDataAccessException dax) {
			log.error("Reference Type ID ==>"+referenceTypeID+"\nEmptyResultDataAccessException encountered in deleteReferenceTypeByID",dax);
			throw new DataNotFoundException();
		}catch(DataIntegrityViolationException dex) {
			log.error("Reference Type ID ==>"+referenceTypeID+"\nDataIntegrityViolationException encountered in deleteReferenceTypeByID",dex);
			throw new DataAccessException();
		}catch(Exception ex) {
			log.error("Reference Type ID ==>"+referenceTypeID+"\nUnknown Exception encountered in deleteReferenceTypeByID",ex);
			throw new UnknownException();
		}
		return deleted;

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