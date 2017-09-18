package com.virginvoyages.dao.impl;

import org.joda.time.LocalDate;
import org.springframework.stereotype.Repository;

import com.virginvoyages.crossreference.model.Audited;
import com.virginvoyages.crossreference.types.ReferenceType;
import com.virginvoyages.dao.IReferenceTypesDAO;

import lombok.extern.slf4j.Slf4j;

/**
 * Data access layer for ReferenceTypes.
 * Stubbed out now as data source yet to be finalized
 * @author snarthu
 *
 */
@Repository
@Slf4j
public class ReferenceTypeDAOImpl implements IReferenceTypesDAO {

	/* (non-Javadoc)
	 * @see com.virginvoyages.dao.IReferenceTypesDAO#addReferenceTypeToReferenceTypes(com.virginvoyages.crossreference.types.ReferenceType)
	 * @param referenceType
	 */
	@Override
	public void addReferenceTypeToReferenceTypes(ReferenceType referenceType) {

		log.debug("adding referenceType to ReferenceTypes ");
		createReferenceType(referenceType);

	}

	/**
	 * @param referenceType
	 */
	private void createReferenceType(ReferenceType referenceType) {

		referenceType.auditData(createAuditDataForCreate());
		referenceType.referenceName("RN1");
		referenceType.referenceTypeID("RT1");
		referenceType.referenceType("Rtype1");

	}

	/**
	 * @return
	 */
	private Audited createAuditDataForCreate() {
		Audited audited = new Audited();
		audited.createDate(LocalDate.now());
		audited.createUser("siva1");
		audited.updateDate(LocalDate.now());
		audited.updateUser("siva2");

		return audited;

	}

}
