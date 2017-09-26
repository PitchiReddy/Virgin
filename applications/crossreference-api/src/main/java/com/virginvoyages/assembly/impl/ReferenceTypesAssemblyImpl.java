package com.virginvoyages.assembly.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Iterables;
import com.virginvoyages.assembly.ReferenceTypesAssembly;
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

	//@Autowired
	//private ReferenceTypesDAO referenceTypeDao;
	
	@Autowired
	private ReferenceTypeRepository referenceTypeRepository;

	/**
	 * Create reference Type based on referenceType. Dummy data being used as of now
	 * - as data source not finalized
	 * 
	 * @param referenceType
	 *            - input referenceType.
	 * @return
	 */

	@Override
	public void addReferenceType(ReferenceType referenceType) {
		log.debug("adding referenceTypes");
		//referenceTypeDao.addReferenceType(referenceType);
		if(!referenceTypeRepository.exists(Long.valueOf(referenceType.referenceTypeID()))) {
			referenceTypeRepository.save(referenceType.convertToDataEntity());
		}
	}

	/**
	 * Find reference Type by ID. Dummy data being used as of now - as data source
	 * not finalized
	 * 
	 * @param referenceTypeID
	 *            - input referenceType.
	 * @return ReferenceType - returns a referenceType
	 */

	@Override
	public ReferenceType findReferenceTypeByID(String referenceTypeID) {
	
		ReferenceTypeData referenceTypeData = referenceTypeRepository
				.findOne(Long.valueOf(referenceTypeID));
		return null == referenceTypeData ? null : referenceTypeData.convertToBusinessEntity();

	}

	/**
	 * Delete reference Type by ID. Dummy data being used as of now - as data source
	 * not finalized
	 * 
	 * @param referenceTypeID
	 *            - input referenceType.
	 * @return
	 */
	public void deleteReferenceTypeByID(String referenceTypeID) {
		log.debug("Entering deleteReferenceTypeByID method in ReferenceTypesAssemblyImpl");
		//referenceTypeDao.deleteReferenceTypeByID(referenceTypeID);
	}

	/**
	 * Update reference Type by ID. Dummy data being used as of now - as data source
	 * not finalized
	 * 
	 * @param referenceType
	 * @param referenceTypeID
	 * @return 
	 */
	@Override
	public void updateReferenceType(String referenceTypeID, ReferenceType referenceType) {
		log.debug("Entering updateReferenceType method in ReferenceTypesAssemblyImpl");
		//referenceTypeDao.updateReferenceType(referenceTypeID, referenceType);
	}

	/**
	 * Finding reference Type. Dummy data being used as of now - as data source not
	 * finalized
	 * 
	 * @return
	 */
	@Override
	public List<ReferenceType> findTypes() {
		log.debug("Entering findTypes method in ReferenceTypesAssemblyImpl");
		//return referenceTypeDao.findTypes();
		//return referenceTypeRepository.findAll();
		//Iterables.addAll(addTo, referenceTypeRepository.findAll().
		//return referenceTypeRepository.findAll().stream().map(sailorID  -> {
          //  return convertAccountDataToSailor(accountClient.findAccount(sailorID),loadPreferences(sailorID),null) ;
		//}).collect(Collectors.toList());
		return new ArrayList<ReferenceType>();
		
	}

}