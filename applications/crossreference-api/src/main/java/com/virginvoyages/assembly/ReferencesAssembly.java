package com.virginvoyages.assembly;

import com.virginvoyages.crossreference.references.Reference;

/**
 * {@code Interface} for assembly tasks for References
 * 
 * @author snarthu
 *
 */
public interface ReferencesAssembly {

	public void addReference(Reference reference);

	public Reference findReferenceID(String referenceID);
}
