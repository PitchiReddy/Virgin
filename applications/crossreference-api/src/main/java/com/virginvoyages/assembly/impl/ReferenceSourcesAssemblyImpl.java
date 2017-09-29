package com.virginvoyages.assembly.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.virginvoyages.assembly.ReferenceSourcesAssembly;
import com.virginvoyages.crossreference.sources.ReferenceSource;
import com.virginvoyages.data.entities.ReferenceSourceData;
import com.virginvoyages.data.repositories.ReferenceSourceRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation for {@link ReferenceSourcesAssemblyImpl}
 * @author pbovilla
 */
@Service
@Slf4j
public class ReferenceSourcesAssemblyImpl implements ReferenceSourcesAssembly {

/*	@Autowired
	private ReferenceSourcesDAO referenceSourcesDAO;*/
	
	@Autowired
	private ReferenceSourceRepository referenceSourceRepository;
	
	 /**
     * Create reference source based on referenceSource. Dummy data being used as of now - as data source not finalized
     * @param referenceSource - input referenceSource.
     * @return 
    */
	@Override
	public void addReferenceSource(ReferenceSource referenceSource) {
		log.debug("Entering addReferenceSource method in ReferenceSourcesAssemblyImpl");
		//referenceSourcesDAO.addReferenceSource(referenceSource);
		
		//if(!referenceSourceRepository.exists(Long.valueOf(referenceSource.referenceSourceID()))) {
			referenceSourceRepository.save(referenceSource.convertToDataEntity());
		//}
		
	}
	
	 /**
     * Find reference source by ID. Dummy data being used as of now - as data source not finalized
     * @param referenceSourceID - input referenceSourceID.
     * @return ReferenceSource - returns a ReferenceSource
    */
	@Override
	public ReferenceSource findReferenceSourceByID(String referenceSourceID) {
		log.debug("Entering findReferenceSourceByID method in ReferenceSourcesAssemblyImpl");
		//ReferenceSource getReferenceSource = referenceSourcesDAO.findReferenceSourceByID(referenceSourceID);
		ReferenceSourceData referenceSourceData = referenceSourceRepository
				.findOne(Long.valueOf(referenceSourceID));
		return null == referenceSourceData ? null : referenceSourceData.convertToBusinessEntity();
	}
	
	 /**
     * Remove the ReferenceSource. Dummy data being used as of now - as data source not finalized
     * @param referenceSourceID - input referenceSourceID.
     * @return
    */
	@Override
	public void deleteReferenceSourceByID(String referenceSourceID) {
		log.debug("Entering deleteReferenceSourceByID method in ReferenceSourcesAssemblyImpl");
		//referenceSourcesDAO.deleteReferenceSourceByID(referenceSourceID);
	}

	 /**
     * Update a `ReferenceSource` object . Dummy data being used as of now - as data source not finalized
     * @param referenceSourceID - input referenceSourceID.
     * @param referenceSource - input referenceSource.
     * @return
    */
	@Override
	public void updateReferenceSource(String referenceSourceID, ReferenceSource referenceSource) {
		log.debug("Entering updateReferenceSource method in ReferenceSourcesAssemblyImpl");
		//referenceSourcesDAO.updateReferenceSource(referenceSourceID,referenceSource);
		
	}

	/**
     * Gets `Source` objects. . Dummy data being used as of now - as data source not finalized
     * @return List<ReferenceSource>
    */
	@Override
	public List<ReferenceSource> findSources() {
		log.debug("Entering findSources method in ReferenceSourcesAssemblyImpl");
		//return referenceSourcesDAO.findSources();
		return null;
	}
	
}
