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
     * @param referenceSource - input sailor id.
     * @return 
    */
	@Override
	public void addReferenceSource(ReferenceSource referenceSource) {
		// TODO Auto-generated method stub
		log.debug("Entering addReferenceSource method in ReferenceSourcesAssemblyImpl");
		referenceSourcesDAO.addReferenceSource(referenceSource);
		
	}

}
