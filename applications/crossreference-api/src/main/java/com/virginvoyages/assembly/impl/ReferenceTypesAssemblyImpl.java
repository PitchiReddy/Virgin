package com.virginvoyages.assembly.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.virginvoyages.assembly.ReferenceTypesAssembly;
import com.virginvoyages.crossreference.types.ReferenceType;
import com.virginvoyages.dao.ReferenceTypesDAO;

import lombok.extern.slf4j.Slf4j;

/**
 * Implementation for {@link ReferenceTypesAssembly}
 * @author snarthu
 *
 */
@Service
@Slf4j
public class ReferenceTypesAssemblyImpl implements ReferenceTypesAssembly {

	@Autowired
	private ReferenceTypesDAO referenceTypeDao;
	
	
	@Override
	public void addReferenceType(ReferenceType referenceType) {
		
		log.debug("adding referenceTypes");
		referenceTypeDao.addReferenceType(referenceType);
		
	}


	@Override
	public ReferenceType findReferenceTypeByID(String referenceTypeID) {
		
		return referenceTypeDao.findReferenceTypeByID(referenceTypeID);
	}

	
}