package com.virginvoyages.assembly;

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
}
