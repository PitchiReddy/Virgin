package com.virginvoyages.assembly.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.virginvoyages.assembly.ReferenceTypesAssembly;
import com.virginvoyages.crossreference.types.ReferenceType;
import com.virginvoyages.dao.ReferenceTypesDAO;

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
	private ReferenceTypesDAO referenceTypeDao;

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

		log.debug("Entering addReferenceType method in ReferenceTypesAssemblyImpl");
		referenceTypeDao.addReferenceType(referenceType);

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
		log.debug("Entering findReferenceTypeByID method in ReferenceTypesAssemblyImpl");
		return referenceTypeDao.findReferenceTypeByID(referenceTypeID);

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
		referenceTypeDao.deleteReferenceTypeByID(referenceTypeID);
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
		referenceTypeDao.updateReferenceType(referenceTypeID, referenceType);
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
		return referenceTypeDao.findTypes();
	}

}