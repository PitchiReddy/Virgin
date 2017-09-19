/**
 * 
 */
package com.virginvoyages.assembly.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.virginvoyages.assembly.ReferencesAssembly;
import com.virginvoyages.crossreference.references.Reference;
import com.virginvoyages.dao.ReferencesDAO;

import lombok.extern.slf4j.Slf4j;

/**
 * Implementation for {@link ReferencesAssembly}
 * @author snarthu
 *
 */
@Service
@Slf4j
public class ReferencesAssemblyImpl implements ReferencesAssembly {

	@Autowired
	private ReferencesDAO referencesDao;

	@Override
	public void addReference(Reference reference) {
		log.debug("adding references");
		referencesDao.addReference(reference);
	}

	@Override
	public Reference findReferenceID(String referenceID) {
		return	referencesDao.findReferenceID(referenceID);
	}

}
