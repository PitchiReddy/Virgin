package com.virginvoyages.assembly;

import com.virginvoyages.crossreference.references.Reference;

/**
 * @author snarthu
 *
 */
public interface ReferencesAssembly {

	public void addReference(Reference reference);

	public Reference findReferenceID(String referenceID);
}
