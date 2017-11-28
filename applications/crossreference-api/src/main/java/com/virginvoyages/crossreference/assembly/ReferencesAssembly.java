package com.virginvoyages.crossreference.assembly;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.virginvoyages.crossreference.model.Reference;

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

	public List<Reference> findReferences(Pageable pageable);

	public List<Reference> findReferenceByMasterId(String masterID, String targetTypeID);

	public Reference updateReference(Reference body);

	public List<Reference> findReferencesTypeAndTargetType(Reference reference);
}
