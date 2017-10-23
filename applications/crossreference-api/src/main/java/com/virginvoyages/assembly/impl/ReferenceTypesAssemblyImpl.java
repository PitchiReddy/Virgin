package com.virginvoyages.assembly.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		return null == referenceTypeData ? null : referenceTypeData.convertToBusinessEntity();

	}

	/**
	 * Delete reference Type by ID. 
	 * @param referenceTypeID
	 *            - input referenceType.
	 * @return
	 */
	public void deleteReferenceTypeByID(String referenceTypeID) {
		log.debug("Entering deleteReferenceTypeByID method in ReferenceTypesAssemblyImpl");
		referenceTypeRepository.delete(referenceTypeID);

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
		ReferenceTypeData referenceTypeData = referenceTypeRepository.save(referenceType.convertToDataEntity());
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