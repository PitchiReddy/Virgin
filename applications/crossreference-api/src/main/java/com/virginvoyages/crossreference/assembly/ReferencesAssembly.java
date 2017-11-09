package com.virginvoyages.crossreference.assembly;

import java.util.List;

import com.virginvoyages.model.crossreference.Reference;


/**
 * {@code Interface} for assembly tasks for References
 * 
 * @author snarthu
 *
 */
public interface ReferencesAssembly {

	public Reference addReference(Reference reference);

	public Reference findReferenceByID(String referenceID);

	public void deleteReferenceByID(String referenceID);

	public List<Reference> findReferences();

	public List<Reference> findReferencesByMaster(String masterID);

	public Reference updateReference(Reference body);

	public List<Reference> findReferencesBySource(Reference reference);

	public List<Reference> findReferencesSourceAndTargetSource(Reference reference);
}
