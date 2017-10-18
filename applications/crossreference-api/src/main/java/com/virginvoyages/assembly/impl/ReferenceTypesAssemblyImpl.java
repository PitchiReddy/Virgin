package com.virginvoyages.assembly.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.virginvoyages.assembly.ReferenceTypesAssembly;
import com.virginvoyages.crossreference.exceptions.DataNotFoundException;
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
		ReferenceTypeData referenceTypeData =	referenceTypeRepository.save(referenceType.convertToDataEntity());
		return referenceTypeData.convertToBusinessEntity();

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
		ReferenceTypeData referenceTypeData = referenceTypeRepository.findOne(referenceTypeID);
		if(null!=referenceTypeData) {
			referenceTypeRepository.delete(referenceTypeID);
		}
		else {
			throw new DataNotFoundException();
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
		 referenceTypeData = referenceTypeRepository.save(referenceType.convertToUpdateDataEntity(referenceType.referenceTypeID()));
		}
		else {
			throw new DataNotFoundException();
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
		Iterable<ReferenceTypeData> referenceTypeDataIterable = referenceTypeRepository.findAll();
		List<ReferenceType> listOfReferenceType = new ArrayList<>();
		for (ReferenceTypeData referenceTypeData : referenceTypeDataIterable) {
			listOfReferenceType.add(referenceTypeData.convertToBusinessEntity());
		}
		return null == referenceTypeDataIterable ? null : listOfReferenceType;

	}

}