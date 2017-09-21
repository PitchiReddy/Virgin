/**
 * 
 */
package com.virginvoyages.dao;

import com.virginvoyages.crossreference.references.Reference;

/**
 * {@code Interface} for DAO tasks for Reference
 * @author snarthu
 *
 */
public interface ReferencesDAO {

public	void addReference(Reference reference);

public Reference findReferenceID(String referenceID);

}
