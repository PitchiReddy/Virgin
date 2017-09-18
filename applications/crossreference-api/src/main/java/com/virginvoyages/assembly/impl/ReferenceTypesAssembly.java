package com.virginvoyages.assembly.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.virginvoyages.assembly.IReferenceTypesAssembly;
import com.virginvoyages.crossreference.types.ReferenceType;
import com.virginvoyages.dao.IReferenceTypesDAO;

import lombok.extern.slf4j.Slf4j;

/**
 * Implementation for {@link IReferenceTypesAssembly}
 * @author snarthu
 *
 */
@Service
@Slf4j
public class ReferenceTypesAssembly implements IReferenceTypesAssembly {

	@Autowired
	private IReferenceTypesDAO referenceTypeDao;
	
	
	@Override
	public void addReferenceTypeToReferenceTypes(ReferenceType referenceType) {
		
		log.debug("adding referenceTypes");
		referenceTypeDao.addReferenceTypeToReferenceTypes(referenceType);
		
	}

	
}