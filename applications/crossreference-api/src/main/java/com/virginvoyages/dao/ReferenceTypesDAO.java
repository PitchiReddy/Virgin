package com.virginvoyages.dao;

import java.util.List;

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

	public void deleteReferenceTypeByID(String referenceTypeID);

	public void updateReferenceType(String referenceTypeID, ReferenceType referenceType);

	public List<ReferenceType> findTypes();

	

}
