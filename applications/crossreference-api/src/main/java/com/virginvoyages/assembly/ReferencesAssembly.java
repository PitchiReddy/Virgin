package com.virginvoyages.assembly;

import java.util.List;

import com.virginvoyages.crossreference.references.Reference;
import com.virginvoyages.crossreference.references.References;

/**
 * {@code Interface} for assembly tasks for References
 * 
 * @author snarthu
 *
 */
public interface ReferencesAssembly {

	public void addReference(Reference reference);

	public Reference findReferenceByID(String referenceID);

	public void deleteReferenceByID(String referenceID);

	public References findReferences();

	public List<Reference> findReferencesByMaster(String masterID);

	public void updateReference(Reference body);
}
