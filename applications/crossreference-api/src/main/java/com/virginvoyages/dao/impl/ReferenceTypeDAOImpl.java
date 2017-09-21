package com.virginvoyages.dao.impl;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;

import com.virginvoyages.crossreference.types.ReferenceType;
import com.virginvoyages.dao.ReferenceTypesDAO;

import lombok.extern.slf4j.Slf4j;

/**
 * Data access layer for ReferenceTypes. Stubbed out now as data source yet to
 * be finalized
 * 
 * @author snarthu
 *
 */
@Repository
@Slf4j
public class ReferenceTypeDAOImpl implements ReferenceTypesDAO {

	private Map<Object, ReferenceType> parameters = new HashMap<>();

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

		log.debug("adding referenceType to ReferenceTypes ");
		createReferenceType(referenceType);

	}
	
	 //TODO remove once data source finalized
	/**
	 * Temporary method - will be removed
	 * @param referenceTypeID
	 *            - input referenceType.
	 * @return ReferenceType - returns a referenceType
	 */
	private void createReferenceType(ReferenceType referenceType) {

		parameters.put(referenceType.referenceTypeID(), referenceType);
		parameters.put(referenceType.referenceName(), referenceType);
		parameters.put(referenceType.referenceType(), referenceType);
		parameters.put(referenceType.auditData(), referenceType);

	}

	@Override
	public ReferenceType findReferenceTypeByID(String referenceTypeID) {
		return parameters.get(referenceTypeID);

	}

}
