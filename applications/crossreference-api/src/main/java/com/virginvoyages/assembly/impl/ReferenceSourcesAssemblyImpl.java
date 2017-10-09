package com.virginvoyages.assembly.impl;

import java.util.ArrayList;
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
	public void addReferenceSource(ReferenceSource referenceSource) {
		log.debug("Entering addReferenceSource method in ReferenceSourcesAssemblyImpl");
		referenceSourceRepository.save(referenceSource.convertToDataEntity());

	}

	/**
	 * Find reference source by ID.
	 * 
	 * @param referenceSourceID
	 *            - input referenceSourceID.
	 * @return ReferenceSource - returns a ReferenceSource
	 */
	@Override
	public ReferenceSource findReferenceSourceByID(String referenceSourceID) {
		log.debug("Entering findReferenceSourceByID method in ReferenceSourcesAssemblyImpl");
		ReferenceSourceData referenceSourceData = referenceSourceRepository.findOne(Long.valueOf(referenceSourceID));
		return null == referenceSourceData ? null : referenceSourceData.convertToBusinessEntity();
	}

	/**
	 * Remove the ReferenceSource.
	 * 
	 * @param referenceSourceID
	 *            - input referenceSourceID.
	 * @return
	 */
	@Override
	public void deleteReferenceSourceByID(String referenceSourceID) {
		log.debug("Entering deleteReferenceSourceByID method in ReferenceSourcesAssemblyImpl");
		long convertReferenceSourceID = Long.parseLong(referenceSourceID);
		referenceSourceRepository.delete(convertReferenceSourceID);
	}

	/**
	 * Update a `ReferenceSource` object .
	 * 
	 * @param referenceSourceID
	 *            - input referenceSourceID.
	 * @param referenceSource
	 *            - input referenceSource.
	 * @return
	 */
	@Override
	public void updateReferenceSource(String referenceSourceID, ReferenceSource referenceSource) {
		log.debug("Entering updateReferenceSource method in ReferenceSourcesAssemblyImpl");
		referenceSourceRepository.save(referenceSource.convertToUpdateDataEntity(referenceSourceID));
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
		Iterable<ReferenceSourceData> referenceSourceDataIterable = referenceSourceRepository.findAll();
		List<ReferenceSource> listOfReferenceSource = new ArrayList<>();
		for (ReferenceSourceData referenceSourceData : referenceSourceDataIterable) {
			listOfReferenceSource.add(referenceSourceData.convertToBusinessEntity());
		}
		return null == referenceSourceDataIterable ? null : listOfReferenceSource;
	}

}
