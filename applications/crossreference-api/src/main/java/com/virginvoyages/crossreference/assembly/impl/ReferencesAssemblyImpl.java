/**
 *
 */
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
import org.springframework.stereotype.Service;

import com.virginvoyages.crossreference.assembly.ReferencesAssembly;
import com.virginvoyages.crossreference.data.entities.ReferenceData;
import com.virginvoyages.crossreference.data.repositories.ReferenceRepository;
import com.virginvoyages.crossreference.data.repositories.ReferenceTypeRepository;
import com.virginvoyages.crossreference.helper.CrossReferenceEntityMapper;
import com.virginvoyages.crossreference.model.Reference;
import com.virginvoyages.exception.DataAccessException;
import com.virginvoyages.exception.DataInsertionException;
import com.virginvoyages.exception.DataNotFoundException;
import com.virginvoyages.exception.DataUpdationException;
import com.virginvoyages.exception.UnknownException;

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
	
	@Autowired
	private CrossReferenceEntityMapper entityMapper;
	
	@Autowired
	private ReferenceTypeRepository  referenceTypeRepository;


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
			ReferenceData referenceData	= referenceRepository.save(entityMapper.convertToReferenceDataEntity(reference));
			return (null == referenceData || StringUtils.isBlank(referenceData.referenceID())) ? null : entityMapper.convertToReferenceBusinessEntity(referenceData);
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
			return null == referenceData ? null : entityMapper.convertToReferenceBusinessEntity(referenceData);
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
	 * @param Pageable pageable
	 * @return List<Reference>
	 */
	@Override
	public List<Reference> findReferences(Pageable pageable) {
		log.debug("Entering findReferences method in ReferencesAssemblyImpl");

		try {
			Page<ReferenceData> referenceDataPage = referenceRepository.findAll(pageable);
			return null == referenceDataPage ? Collections.emptyList() :
				Optional.ofNullable(referenceDataPage.getContent()).orElseGet(Collections::emptyList).stream()
				.map(referenceData -> entityMapper.convertToReferenceBusinessEntity(referenceData)).collect(Collectors.toList());
		}catch(Exception ex) {
			log.error("Exception encountered in findReferences",ex);
			throw new UnknownException();
		}
	}

	/**
	 * Find one or more references
	 * @param masterId
	 *            - input masterId.
	 * @param targetTypeID
	 *            - input targetTypeID.
	 * @param pageable
	 *            - input pageable.
	 * @return List<Reference>
	 */
	@Override
	public List<Reference> findReferenceByMasterId(String masterId, String targetTypeID) {
		log.debug("Entering findReferenceByMasterId method in ReferencesAssemblyImpl for masterId ==> "+masterId);
		List<ReferenceData> referenceDataList = null;
		if (!referenceTypeRepository.exists(targetTypeID)) {
			referenceDataList = referenceRepository.findByMasterID(masterId);

		} else {
			referenceDataList = referenceRepository.findByMasterIDAndReferenceTypeDataReferenceTypeID(masterId,
					targetTypeID);
		}
		return Optional.ofNullable(referenceDataList).orElseGet(Collections::emptyList).stream()
				.map(referenceData -> entityMapper.convertToReferenceBusinessEntity(referenceData)).collect(Collectors.toList());
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
			ReferenceData referenceData = referenceRepository.save(entityMapper.convertToReferenceDataEntity(reference));
			return (null == referenceData || StringUtils.isBlank(referenceData.referenceID())) ? null : entityMapper.convertToReferenceBusinessEntity(referenceData);
		}catch(DataIntegrityViolationException dex) {
			log.error("DataIntegrityViolationException encountered while updating reference ",dex);
			throw new DataUpdationException();
		}catch(Exception ex) {
			log.error("Exception encountered while updating reference ",ex);
			throw new UnknownException();
		}
	}
	
	/**
	 * Find one or more references
	 * @param reference
	 *            - input reference.
	 * @return List<Reference>
	 */
	@Override
	public List<Reference> findReferencesTypeAndTargetType(Reference reference) {
		log.debug("Entering findReferencesTypeAndTargetType method in ReferencesAssemblyImpl");
		try {
			Reference referenceForNativeSourceIdValue = findReferenceByNativeSourceIDValueAndType(reference);
			return null == referenceForNativeSourceIdValue ? null
					: Optional.ofNullable(findReferenceByMasterId(referenceForNativeSourceIdValue.masterID(),
							reference.targetReferenceTypeID())).orElseGet(Collections::emptyList);
		} catch (Exception ex) {
			log.error("Exception encountered in findReferencesTypeAndTargetType ", ex);
			throw new UnknownException();
		}
	}
	
	/**
	 * Find one or more references
	 * @param reference
	 *            - input reference.
	 * @return Reference
	 */
	@Override
	public Reference findReferenceByNativeSourceIDValueAndType(Reference reference) {
		log.debug("Entering findReferenceByNativeSourceIDValueAndType method in ReferencesAssemblyImpl");
		try {
			ReferenceData retrieveNativeSourceIDValueAndType = referenceRepository
					.findByNativeSourceIDValueAndReferenceTypeDataReferenceTypeID(reference.nativeSourceIDValue(),
							reference.referenceTypeID());
			return null == retrieveNativeSourceIDValueAndType ? null
					: entityMapper.convertToReferenceBusinessEntity(retrieveNativeSourceIDValueAndType);
		} catch (Exception ex) {
			log.error("Exception encountered in findReferenceByNativeSourceIDValueAndType ", ex);
			throw new UnknownException();
		}
	}

}
