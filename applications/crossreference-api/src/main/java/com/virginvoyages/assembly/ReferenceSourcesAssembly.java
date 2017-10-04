package com.virginvoyages.assembly;

import java.util.List;

import com.virginvoyages.crossreference.sources.ReferenceSource;

/**
 * {@code Interface} for assembly tasks for reference source
 * @author pbovilla
 *
 */
public interface ReferenceSourcesAssembly {

	public void addReferenceSource(ReferenceSource referenceSource);
	
	public ReferenceSource findReferenceSourceByID(String referenceSourceID);
	
	public void deleteReferenceSourceByID(String referenceSourceID);
	
	public void updateReferenceSource(String referenceSourceID,ReferenceSource referenceSource);
	
	public List<ReferenceSource> findSources();
}
