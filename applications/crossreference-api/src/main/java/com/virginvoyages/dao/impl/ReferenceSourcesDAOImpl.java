package com.virginvoyages.dao.impl;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
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
	
	@Override
	public ReferenceSource findReferenceSourceByID(String referenceSourceID) {
		// TODO Auto-generated method stub
		log.debug("Entering findReferenceSourceByID method in ReferenceSourcesDAOImpl");
		return parameters.get(referenceSourceID);
		
	}
}
