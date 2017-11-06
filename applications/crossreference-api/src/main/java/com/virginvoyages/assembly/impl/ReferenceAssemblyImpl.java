package com.virginvoyages.assembly.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.virginvoyages.assembly.ReferenceAssembly;
import com.virginvoyages.crossreference.references.Reference;
import com.virginvoyages.data.entities.ReferenceData;
import com.virginvoyages.data.repositories.ReferenceRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReferenceAssemblyImpl implements ReferenceAssembly{

	@Autowired
	private ReferenceRepository refRepo;
	
	@Override
	public List<Reference> findReferenceByMasterId(String masterId) {
		List<ReferenceData> referenceDataList =  refRepo.findByMasterID(masterId);
	    List<Reference> referencesList = new ArrayList<>();
	  return Optional.ofNullable(referenceDataList).orElseGet(Collections::emptyList).
	  stream().map(referenceData -> referenceData.convertToBusinessEntity()).collect(Collectors.toList());
	}

}
