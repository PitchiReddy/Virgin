/**
 * 
 */
package com.virginvoyages.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Repository;

import com.virginvoyages.crossreference.model.Audited;
import com.virginvoyages.crossreference.references.Reference;
import com.virginvoyages.crossreference.references.References;
import com.virginvoyages.crossreference.references.ReferencesEmbedded;
import com.virginvoyages.crossreference.types.ReferenceType;
import com.virginvoyages.dao.ReferencesDAO;
import com.virginvoyages.model.Page;

import lombok.extern.slf4j.Slf4j;

/**
 * Data access layer for ReferenceTypes.
 * Stubbed out now as data source yet to be finalized
 * @author snarthu
 *
 */
/**
 * @author snarthu
 *
 */
@Repository
@Slf4j
public class ReferencesDAOImpl implements ReferencesDAO {

	private Map<Object, Reference> parameters = new HashMap<>();

	/**
	 * Create reference based on reference. Dummy data being used as of now - as
	 * data source not finalized
	 * 
	 * @param reference
	 *            - input reference.
	 * @return
	 */
	@Override
	public void addReference(Reference reference) {
		log.debug("Entering addReference method in ReferencesDAOImpl");
		getDataForCreateReference(reference);
	}

	// TODO remove once data source finalized
	/**
	 * Temporary method - will be removed
	 * 
	 * @param referenceID
	 *            - input reference.
	 * @return
	 */
	private void getDataForCreateReference(Reference reference) {

		parameters.put(reference.referenceID(), reference);
		parameters.put(reference.masterID(), reference);
		parameters.put(reference.nativeSourceID(), reference);
		parameters.put(reference.details(), reference);
		parameters.put(reference.expiry(), reference);
		parameters.put(reference.referenceSource(), reference);
		parameters.put(reference.referenceType(), reference);
		parameters.put(reference.auditData(), reference);

	}

	// TODO remove once data source finalized
	/**
	 * Temporary method - will be removed
	 * 
	 * @param referenceID
	 *            - input reference.
	 * @return Reference - returns a reference
	 */
	@Override
	public Reference findReferenceByID(String referenceID) {
		log.debug("Entering findReferenceByID method in ReferencesDAOImpl");
		return parameters.get(referenceID);
	}

	// TODO remove once data source finalized
	/**
	 * Temporary method - will be removed
	 * 
	 * @param referenceID
	 *            - input reference.
	 * @return
	 */
	@Override
	public void deleteReferenceByID(String referenceID) {
		log.debug("Entering deleteReferenceByID method in ReferencesDAOImpl");
		parameters.remove(referenceID);

	}

	// TODO remove once data source finalized
	/**
	 * Temporary method - will be removed
	 * 
	 * @param referenceID
	 * @param reference
	 *            - input referenceID and reference
	 * @return
	 */
	@Override
	public void updateReference(String referenceID, Reference reference) {
		log.debug("Entering updateReference method in ReferencesDAOImpl");
		Reference existingReference = parameters.get(referenceID);
		if (null != existingReference)
			reference.auditData(updateAuditDataForUpdate(existingReference.auditData()));
		parameters.put(referenceID, reference);
	}

	private Audited updateAuditDataForUpdate(Audited audited) {
		if (null == audited) {
			audited = createAuditDataForCreate();
		}
		return audited.updateDate(LocalDate.now()).updateUser("siva");
	}

	private Audited createAuditDataForCreate() {
		return new Audited().createDate(LocalDate.now()).createUser("shankar");
	}

	// TODO remove once data source finalized
	/**
	 * Temporary method - will be removed
	 * 
	 * @param masterID
	 * @param targetSourceID
	 *            - input masterID and targetSourceID
	 * @return List of Reference
	 */
	@Override
	public List<Reference> findReferencesByMaster(String masterID, String targetSourceID) {
		log.debug("Entering findReferencesByMaster method in ReferencesDAOImpl");
		if (StringUtils.isNotEmpty(targetSourceID)) {
			return findReferencesByMasterAndTargetSourceID(masterID, targetSourceID);
		}
		return parameters.values().stream().filter(e -> e.masterID().equals(masterID)).collect(Collectors.toList());
	}

	private List<Reference> findReferencesByMasterAndTargetSourceID(String masterID, String targetSourceID) {
		return parameters.values().stream().filter(
				e -> e.masterID().equals(masterID) && e.referenceSource().referenceSourceID().equals(targetSourceID))
				.collect(Collectors.toList());
	}

	// TODO remove once data source finalized
	/**
	 * @param page
	 * @param size
	 * @return List of References Temporary method - will be removed
	 */
	@Override
	public References findReferences(Integer page, Integer size) {
		log.debug("Entering findReferences method in ReferencesDAOImpl");
		List<Reference> referenceList = parameters.values().stream().collect(Collectors.toList());
		References references = new References().page(new Page().size(size))
				.embedded(new ReferencesEmbedded().references(referenceList));
		return references;
	}

}
