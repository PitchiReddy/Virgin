package com.virginvoyages.dao;

import com.virginvoyages.crossreference.types.ReferenceType;

/**
 * {@code Interface} for DAO tasks for ReferenceType
 * 
 * @author snarthu
 *
 */
public interface ReferenceTypesDAO {

	public void addReferenceType(ReferenceType referenceType);

	public ReferenceType findReferenceTypeByID(String referenceTypeID);

}
