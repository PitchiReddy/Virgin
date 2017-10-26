package com.virginvoyages.assembly.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import com.virginvoyages.assembly.ReferenceSourcesAssembly;
import com.virginvoyages.crossreference.exceptions.DataNotFoundException;
import com.virginvoyages.crossreference.exceptions.DataUpdationException;
import com.virginvoyages.crossreference.sources.ReferenceSource;
import com.virginvoyages.data.entities.ReferenceSourceData;
import com.virginvoyages.data.repositories.ReferenceSourceRepository;
import com.virginvoyages.crossreference.exceptions.DataAccessException;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation for {@link ReferenceSourcesAssemblyImpl}
 * 
 * @author pbovilla
 */
@Service
@Slf4j
public class ReferenceSourcesAssemblyImpl implements ReferenceSourcesAssembly {

	@Autowired
	private ReferenceSourceRepository referenceSourceRepository;

	/**
	 * Create reference source based on referenceSource.
	 * 
	 * @param referenceSource
	 *            - input referenceSource.
	 * @return
	 */
	@Override
	public ReferenceSource addReferenceSource(ReferenceSource referenceSource) {
		log.debug("Entering addReferenceSource method in ReferenceSourcesAssemblyImpl");
		ReferenceSourceData referenceSourceData	= referenceSourceRepository.save(referenceSource.convertToDataEntity());
		return referenceSourceData.convertToBusinessEntity();
	}

	/**
	 * Find reference source by ID.
	 * @param referenceSourceID
	 *            - input referenceSourceID.
	 * @return ReferenceSource - returns a ReferenceSource
	 */
	@Override
	public ReferenceSource findReferenceSourceByID(String referenceSourceID) {
		log.debug("Entering findReferenceSourceByID method in ReferenceSourcesAssemblyImpl");
		ReferenceSourceData referenceSourceData = referenceSourceRepository.findOne(referenceSourceID);
		 if(referenceSourceData==null) {
			 throw new DataNotFoundException();
		 }
		return  referenceSourceData.convertToBusinessEntity();
	}

	/**
	 * Remove the ReferenceSource.
	 * @param referenceSourceID
	 *            - input referenceSourceID.
	 * @return
	 */
	@Override
	public void deleteReferenceSourceByID(String referenceSourceID) {
		log.debug("Entering deleteReferenceSourceByID method in ReferenceSourcesAssemblyImpl");
		try{
			referenceSourceRepository.delete(referenceSourceID);
		}
		catch(EmptyResultDataAccessException erdae) {
			throw new DataNotFoundException();
		}
		catch(DataIntegrityViolationException die) {
			throw new DataAccessException();
		}
		
	}

	/**
	 * Update a `ReferenceSource` object.
	 * @param referenceSourceID
	 *            - input referenceSourceID.
	 * @param referenceSource
	 *            - input referenceSource.
	 * @return
	 */
	@Override
	public ReferenceSource updateReferenceSource(ReferenceSource referenceSource) {
		log.debug("Entering updateReferenceSource method in ReferenceSourcesAssemblyImpl");
		ReferenceSourceData referenceSourceData = null;
		ReferenceSourceData findReferenceSourceData = referenceSourceRepository.findOne(referenceSource.referenceSourceID());
		if(null!=findReferenceSourceData) {
		referenceSourceData = referenceSourceRepository.save(referenceSource.convertToDataEntity());
		}else {
			throw new DataUpdationException();
		}
		return referenceSourceData.convertToBusinessEntity();
	}

	/**
	 * Gets `Source` objects.
	 * 
	 * @param <T>
	 * @return List<ReferenceSource>
	 */
	@Override
	public List<ReferenceSource> findSources() {
		log.debug("Entering findSources method in ReferenceSourcesAssemblyImpl");
		List<ReferenceSourceData> listOfReferenceSourceData = (List<ReferenceSourceData>)referenceSourceRepository.findAll();
		List<ReferenceSource> listOfReferenceSource = new ArrayList<>();
		if(null != listOfReferenceSourceData && listOfReferenceSourceData.size() > 0 ) {
			listOfReferenceSource = listOfReferenceSourceData.stream().map(referenceSourceData -> referenceSourceData.convertToBusinessEntity()).collect(Collectors.toList());
		}
		return listOfReferenceSource;
		
	}

}
