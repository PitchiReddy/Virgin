/**
 * 
 */
package com.virginvoyages.dao;

import java.util.List;

import com.virginvoyages.crossreference.references.Reference;
import com.virginvoyages.crossreference.references.References;

/**
 * {@code Interface} for DAO tasks for Reference
 * @author snarthu
 *
 */
public interface ReferencesDAO {

public	void addReference(Reference reference);

public Reference findReferenceByID(String referenceID);

void deleteReferenceByID(String referenceID);

public void updateReference(String referenceID, Reference reference);

public List<Reference> findReferencesByMaster(String masterID, String targetSourceID);

public References findReferences(Integer page, Integer size);

}
