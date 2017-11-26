package com.virginvoyages.crossreference.assembly;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.virginvoyages.crossreference.model.ReferenceSource;

/**
 * {@code Interface} for assembly tasks for reference source
 * @author pbovilla
 *
 */
public interface ReferenceSourcesAssembly {

	public ReferenceSource addReferenceSource(ReferenceSource referenceSource);
	
	public ReferenceSource findReferenceSourceByID(String referenceSourceID);
	
	public ReferenceSource findReferenceSourceByName(String referenceSourceName);
		
	public boolean deleteReferenceSourceByID(String referenceSourceID);
	
	public ReferenceSource updateReferenceSource(ReferenceSource referenceSource);
	
	public List<ReferenceSource> findSources(Pageable pageable);
}
