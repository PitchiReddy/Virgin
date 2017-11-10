package com.virginvoyages.assembly.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.virginvoyages.assembly.ReferenceAssembly;
import com.virginvoyages.crossreference.data.entities.ReferenceData;
import com.virginvoyages.data.repositories.ReferenceRepository;
import com.virginvoyages.model.crossreference.Reference;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReferenceAssemblyImpl implements ReferenceAssembly{

	@Autowired
	private ReferenceRepository refRepo;
	
	@Override
	public List<Reference> findReferenceByMasterId(String masterId, Pageable pageable) {
		Page<ReferenceData> referenceDataPage =  refRepo.findByMasterID(masterId,pageable);
	return Optional.ofNullable(referenceDataPage.getContent()).orElseGet(Collections::emptyList).
	  stream().map(referenceData -> referenceData.convertToBusinessEntity()).collect(Collectors.toList());
	}

}
