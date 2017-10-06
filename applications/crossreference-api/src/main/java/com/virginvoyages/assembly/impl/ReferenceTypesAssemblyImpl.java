package com.virginvoyages.assembly.impl;

import java.util.ArrayList;
import java.util.List;
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
		referenceTypeRepository.save(referenceType.convertToDataEntity());

	}

	/**
	 * Find reference Type by ID. not finalized
	 * 
	 * @param referenceTypeID
	 *            - input referenceType.
	 * @return ReferenceType - returns a referenceType
	 */

	@Override
	public ReferenceType findReferenceTypeByID(String referenceTypeID) {

		ReferenceTypeData referenceTypeData = referenceTypeRepository.findOne(Long.valueOf(referenceTypeID));
		return null == referenceTypeData ? null : referenceTypeData.convertToBusinessEntity();

	}

	/**
	 * Delete reference Type by ID. not finalized
	 * 
	 * @param referenceTypeID
	 *            - input referenceType.
	 * @return
	 */
	public void deleteReferenceTypeByID(String referenceTypeID) {
		log.debug("Entering deleteReferenceTypeByID method in ReferenceTypesAssemblyImpl");
		long convertReferenceTypeID = Long.parseLong(referenceTypeID);
		referenceTypeRepository.delete(convertReferenceTypeID);

	}

	/**
	 * Update reference Type by ID. not finalized
	 * 
	 * @param referenceType
	 * @param referenceTypeID
	 * @return
	 */
	@Override
	public void updateReferenceType(String referenceTypeID, ReferenceType referenceType) {
		log.debug("Entering updateReferenceType method in ReferenceTypesAssemblyImpl");

	}

	/**
	 * Finding reference Type. finalized
	 * 
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