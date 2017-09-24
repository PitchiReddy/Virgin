package com.virginvoyages.assembly.impl;

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

}