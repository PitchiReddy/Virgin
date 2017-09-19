package com.virginvoyages.assembly;

import com.virginvoyages.crossreference.types.ReferenceType;

public interface ReferenceTypesAssembly {

	public void addReferenceType(ReferenceType referenceType);

	public ReferenceType findReferenceTypeByID(String referenceTypeID);
}
