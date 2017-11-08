package com.virginvoyages.assembly;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.virginvoyages.crossreference.references.Reference;

public interface ReferenceAssembly {

	public List<Reference> findReferenceByMasterId(String masterId, Pageable pageable);
	
}
