package com.virginvoyages.assembly;

import java.util.List;

import com.virginvoyages.crossreference.types.ReferenceType;

/**
 * {@code Interface} for assembly tasks for ReferenceType
 * 
 * @author snarthu
 *
 */
public interface ReferenceTypesAssembly {

	public void addReferenceType(ReferenceType referenceType);

	public ReferenceType findReferenceTypeByID(String referenceTypeID);

	public void deleteReferenceTypeByID(String referenceTypeID);

	public void updateReferenceType(String referenceTypeID, ReferenceType body);

	public List<ReferenceType> findTypes();
}
