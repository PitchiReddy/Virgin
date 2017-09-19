package com.virginvoyages.dao.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.virginvoyages.crossreference.types.ReferenceType;
import com.virginvoyages.dao.ReferenceTypesDAO;

import lombok.extern.slf4j.Slf4j;

/**
 * Data access layer for ReferenceTypes.
 * Stubbed out now as data source yet to be finalized
 * @author snarthu
 *
 */
@Repository
@Slf4j
public class ReferenceTypeDAOImpl implements ReferenceTypesDAO {

	private Map<Object, ReferenceType> parameters = new ConcurrentHashMap<>();
	/* (non-Javadoc)
	 * @see com.virginvoyages.dao.IReferenceTypesDAO#addReferenceTypeToReferenceTypes(com.virginvoyages.crossreference.types.ReferenceType)
	 * @param referenceType
	 */
	@Override
	public void addReferenceType(ReferenceType referenceType) {

		log.debug("adding referenceType to ReferenceTypes ");
		createReferenceType(referenceType);

	}

	/**
	 * @param referenceType
	 */
	private void createReferenceType(ReferenceType referenceType) {

		parameters.put(referenceType.referenceTypeID(),referenceType);
		parameters.put(referenceType.referenceName(),referenceType);
		parameters.put(referenceType.referenceType(),referenceType);
		parameters.put(referenceType.auditData(),referenceType);
		
	}

	@Override
	public ReferenceType findReferenceTypeByID(String referenceTypeID) {
		ReferenceType referenceType =parameters.get(referenceTypeID);
		return referenceType;
	}

	

}
