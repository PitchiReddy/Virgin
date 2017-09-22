package com.virginvoyages.assembly.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.virginvoyages.assembly.ReferenceSourcesAssembly;
import com.virginvoyages.crossreference.sources.ReferenceSource;
import com.virginvoyages.dao.ReferenceSourcesDAO;

import lombok.extern.slf4j.Slf4j;

/**
 * Implementation for {@link ReferenceSourcesAssemblyImpl}
 * @author pbovilla
 */
@Service
@Slf4j
public class ReferenceSourcesAssemblyImpl implements ReferenceSourcesAssembly {

	@Autowired
	private ReferenceSourcesDAO referenceSourcesDAO;
	
	 /**
     * Create reference source based on referenceSource. Dummy data being used as of now - as data source not finalized
     * @param referenceSource - input referenceSource.
     * @return 
    */
	@Override
	public void addReferenceSource(ReferenceSource referenceSource) {
		log.debug("Entering addReferenceSource method in ReferenceSourcesAssemblyImpl");
		referenceSourcesDAO.addReferenceSource(referenceSource);
		
	}
	
	 /**
     * Find reference source by ID. Dummy data being used as of now - as data source not finalized
     * @param referenceSourceID - input referenceSourceID.
     * @return ReferenceSource - returns a ReferenceSource
    */
	@Override
	public ReferenceSource findReferenceSourceByID(String referenceSourceID) {
		log.debug("Entering findReferenceSourceByID method in ReferenceSourcesAssemblyImpl");
		ReferenceSource getReferenceSource = referenceSourcesDAO.findReferenceSourceByID(referenceSourceID);
		return getReferenceSource;
	}
	
}
