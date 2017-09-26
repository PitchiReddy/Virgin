package com.virginvoyages.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.joda.time.LocalDate;
import org.springframework.stereotype.Repository;

import com.virginvoyages.crossreference.model.Audited;
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

		log.debug("Entering addReferenceType method in ReferenceTypeDAOImpl ");
		createReferenceType(referenceType);

	}
	
	 //TODO remove once data source finalized
	/**
	 * Temporary method - will be removed
	 * @param referenceType
	 *            - input referenceType.
	 * @return 
	 */
	private void createReferenceType(ReferenceType referenceType) {

		parameters.put(referenceType.referenceTypeID(), referenceType);
		parameters.put(referenceType.referenceName(), referenceType);
		parameters.put(referenceType.referenceType(), referenceType);
		parameters.put(referenceType.auditData(), referenceType);

	}
	 //TODO remove once data source finalized
		/**
		 * Temporary method - will be removed
		 * @param referenceTypeID
		 *            - input referenceType.
		 * @return ReferenceType - returns a referenceType
		 */
	@Override
	public ReferenceType findReferenceTypeByID(String referenceTypeID) {
		log.debug("Entering findReferenceTypeByID method in ReferenceTypeDAOImpl ");
		return parameters.get(referenceTypeID);
		
	}

	 //TODO remove once data source finalized
		/**
		 * Temporary method - will be removed
		 * @param referenceTypeID
		 *            - input referenceType.
		 * @return 
		 */
	@Override
	public void deleteReferenceTypeByID(String referenceTypeID) {
		log.debug("Entering deleteReferenceTypeByID method in ReferenceTypeDAOImpl");
		parameters.remove(referenceTypeID);
	}
	
	 //TODO remove once data source finalized
		/**
		 * Temporary method - will be removed
		 * @param referenceTypeID
		 * @param referenceType
		 *            - input referenceTypeID and referenceType
		 * @return 
		 */
	@Override
	public void updateReferenceType(String referenceTypeID, ReferenceType referenceType) {
		log.debug("Entering updateReferenceType method in ReferenceTypeDAOImpl");
		ReferenceType existingReferenceType = parameters.get(referenceTypeID);
		if(null != existingReferenceType)
			referenceType.auditData(updateAuditDataForUpdate(existingReferenceType.auditData()));
			parameters.put(referenceTypeID, referenceType);
	}

	private Audited updateAuditDataForUpdate(Audited audited) {
		if(null == audited) {
			audited = createAuditDataForCreate();
		}
		return audited
			   .updateDate(LocalDate.now())
		 	   .updateUser("siva");
	}

	private Audited createAuditDataForCreate() {
		return new Audited()
				.createDate(LocalDate.now())
				.createUser("shankar");
	}
	/**
	* It will Return List of ReferenceType
	* @return List of ReferenceType
	*/
	@Override
	public List<ReferenceType> findTypes() {
		log.debug("Entering findTypes method in ReferenceTypeDAOImpl");
		return parameters.values().stream().collect(Collectors.toList());
	}

	

}
