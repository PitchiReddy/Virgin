package com.virginvoyages.crossreference.assembly;

import java.util.List;

import org.springframework.data.domain.Pageable;

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

	public List<Reference> findReferenceByMasterId(String masterID, Pageable pageable);

	public Reference updateReference(Reference body);

	public List<Reference> findReferencesBySource(Reference reference);

	public List<Reference> findReferencesSourceAndTargetSource(Reference reference);
}