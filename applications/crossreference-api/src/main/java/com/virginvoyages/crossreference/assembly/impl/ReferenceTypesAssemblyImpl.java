package com.virginvoyages.crossreference.assembly.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
		log.debug("Entering findReferenceTypeByName method in ReferenceTypesAssemblyImpl");
		try {
			ReferenceTypeData referenceTypeData = referenceTypeRepository.findByReferenceType(referenceTypeName);
			return null == referenceTypeData ? null : referenceTypeData.convertToBusinessEntity();
		}catch(Exception ex) {
			log.error("Reference Type ID ==>"+referenceTypeName+"\nException encountered in findReferenceTypeByID",ex);
			throw new UnknownException();
		}
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
			log.error("Reference Type ID ==>"+referenceTypeID+"\n EmptyResultDataAccessException encountered in deleteReferenceTypeByID",dax);
			throw new DataNotFoundException();
		}catch(DataIntegrityViolationException dex) {
			log.error("Reference Type ID ==>"+referenceTypeID+"\n DataIntegrityViolationException encountered in deleteReferenceTypeByID",dex);
			throw new DataAccessException();
		}catch(Exception ex) {
			log.error("Reference Type ID ==>"+referenceTypeID+"\n Unknown Exception encountered in deleteReferenceTypeByID",ex);
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
		
		if(!referenceTypeRepository.exists(referenceType.referenceTypeID())){
			log.error("Reference type does not exist with ID ==> "+referenceType.referenceTypeID());
			throw new DataUpdationException();
		}
		try {
			ReferenceTypeData referenceTypeData = referenceTypeRepository.save(referenceType.convertToDataEntity());
			return (null == referenceTypeData || StringUtils.isBlank(referenceTypeData.referenceTypeID())) ? null : referenceTypeData.convertToBusinessEntity();
		}catch(DataIntegrityViolationException dex) {	
			log.error("DataIntegrityViolationException encountered while updating reference type",dex);
			throw new DataUpdationException();
		}catch(JpaObjectRetrievalFailureException jex) {
			log.error("DataIntegrityViolationException encountered while updating reference type",jex);
			//String errorMessage = null != jex.getRootCause() ? jex.getRootCause().getMessage():jex.getMessage();
			throw new DataUpdationException();
		}
		catch(Exception ex) {
			log.error("Exception encountered while updating reference type",ex);
			throw new UnknownException();
		}
		
	}

	/**
	 * Finding reference Type.  
	 * @return
	 */
	@Override
	public List<ReferenceType> findTypes(Pageable pageable) {
		log.debug("Entering findTypes method in ReferenceTypesAssemblyImpl");
		Page<ReferenceTypeData> referenceTypeDataPage = referenceTypeRepository.findAll(pageable);
			return Optional.ofNullable(referenceTypeDataPage.getContent()).orElseGet(Collections::emptyList).stream()
		.map(referenceData -> referenceData.convertToBusinessEntity()).collect(Collectors.toList());
	}

}