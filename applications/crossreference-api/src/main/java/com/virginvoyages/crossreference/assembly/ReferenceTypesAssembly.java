package com.virginvoyages.crossreference.assembly;

import java.util.List;

import org.springframework.data.domain.Pageable;
import com.virginvoyages.model.crossreference.ReferenceType;

/**
 * {@code Interface} for assembly tasks for ReferenceType
 * 
 * @author snarthu
 *
 */
public interface ReferenceTypesAssembly {

	public ReferenceType addReferenceType(ReferenceType referenceType);

	public ReferenceType findReferenceTypeByID(String referenceTypeID);
	
	public ReferenceType findReferenceTypeByName(String referenceTypeName);

	public boolean deleteReferenceTypeByID(String referenceTypeID);

	public ReferenceType updateReferenceType(ReferenceType body);

	public List<ReferenceType> findTypes(Pageable pageable);
}
