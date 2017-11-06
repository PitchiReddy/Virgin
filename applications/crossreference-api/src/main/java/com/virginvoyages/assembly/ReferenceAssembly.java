package com.virginvoyages.assembly;

import java.util.List;

import com.virginvoyages.crossreference.references.Reference;
import com.virginvoyages.data.entities.ReferenceData;

public interface ReferenceAssembly {

	public List<Reference> findReferenceByMasterId(String masterId);
	
}
