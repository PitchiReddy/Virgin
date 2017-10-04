package com.virginvoyages.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.joda.time.LocalDate;
import org.springframework.stereotype.Service;

import com.virginvoyages.crossreference.model.Audited;
import com.virginvoyages.crossreference.sources.ReferenceSource;
import com.virginvoyages.dao.ReferenceSourcesDAO;
import lombok.extern.slf4j.Slf4j;

/**
 * Data access layer for Cross Reference Source.
 * Stubbed out now as data source yet to be finalized
 * @author pbovilla 
 */
@Service
@Slf4j
public class ReferenceSourcesDAOImpl implements ReferenceSourcesDAO {

	private Map<Object, ReferenceSource> parameters = new HashMap<>();
	/**
	* Mock implementation.
	*/
	public void addReferenceSource(ReferenceSource referenceSource) {
		log.debug("Entering addReferenceSource method in ReferenceSourcesDAOImpl");
		getDataForCreateReferenceSource(referenceSource);
	}
	
    //TODO remove once data source finalized
	/**
	*
	* Temporary method - will be removed
	* @param referenceSource
	* @return
	*/
	private void getDataForCreateReferenceSource(ReferenceSource referenceSource) {

		parameters.put(referenceSource.referenceSourceID(),referenceSource);
		parameters.put(referenceSource.referenceSourceName(),referenceSource);
		parameters.put(referenceSource.inActive(),referenceSource);
		parameters.put(referenceSource.auditData(),referenceSource);
		
	}
	
	/**
	* Mock implementation.
	*/
	@Override
	public ReferenceSource findReferenceSourceByID(String referenceSourceID) {
		log.debug("Entering findReferenceSourceByID method in ReferenceSourcesDAOImpl");
		return parameters.get(referenceSourceID);
		
	}
	
	/**
	* Mock implementation.
	*/
	@Override
	public void deleteReferenceSourceByID(String referenceSourceID) {
		log.debug("Entering deleteReferenceSourceByID method in ReferenceSourcesDAOImpl");
		parameters.remove(referenceSourceID);
	}

	/**
	* Mock implementation.
	*/
	@Override
	public void updateReferenceSource(String referenceSourceID, ReferenceSource referenceSource) {
		log.debug("Entering updateReferenceSource method in ReferenceSourcesDAOImpl");
		ReferenceSource existingReferenceSource = parameters.get(referenceSourceID);
		if(null != existingReferenceSource)
			referenceSource.auditData(updateAuditDataForUpdate(existingReferenceSource.auditData()));
			parameters.put(referenceSourceID, referenceSource);
		
	}
	
	/**
	* Temporary method - will be removed
	* @param audited
	* @return Audited
	*/
	private Audited updateAuditDataForUpdate(Audited audited) {
		if(null == audited) {
			audited = createAuditDataForCreate();
		}
		return audited
			   .updateDate(LocalDate.now())
		 	   .updateUser("mockXREFapi");
		
	}

	/**
	* Temporary method - will be removed
	* @return Audited
	*/
	private Audited createAuditDataForCreate() {
		return new Audited()
				.createDate(LocalDate.now())
				.createUser("mockXREFapi");
	}

	/**
	* Mock implementation.
	*/
	@Override
	public List<ReferenceSource> findSources() {
		log.debug("Entering findSources method in ReferenceSourcesDAOImpl");
		return parameters.values().stream().collect(Collectors.toList());
	}
	
}
