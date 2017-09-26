package com.virginvoyages.dao;

import java.util.List;

import com.virginvoyages.crossreference.sources.ReferenceSource;

/**
 * {@code Interface} for dao tasks for Reference sources operations
 * @author pbovilla
 *
 */
public interface ReferenceSourcesDAO {

	public void addReferenceSource(ReferenceSource referenceSource);
	
	public ReferenceSource findReferenceSourceByID(String referenceSourceID);
	
	public void deleteReferenceSourceByID(String referenceSourceID);
	
	public void updateReferenceSource(String referenceSourceID,ReferenceSource body);
	
	public List<ReferenceSource> findSources();
}
