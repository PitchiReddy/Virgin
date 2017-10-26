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

	public ReferenceType addReferenceType(ReferenceType referenceType);

	public ReferenceType findReferenceTypeByID(String referenceTypeID);

	public void deleteReferenceTypeByID(String referenceTypeID);

	public ReferenceType updateReferenceType(ReferenceType body);

	public List<ReferenceType> findTypes();
}
