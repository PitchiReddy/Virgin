package com.virginvoyages.dao;

import com.virginvoyages.crossreference.types.ReferenceType;

public interface ReferenceTypesDAO {

	public void addReferenceType(ReferenceType referenceType);

	public ReferenceType findReferenceTypeByID(String referenceTypeID);

}
