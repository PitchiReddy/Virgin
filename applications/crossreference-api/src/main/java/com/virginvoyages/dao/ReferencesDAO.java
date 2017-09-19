/**
 * 
 */
package com.virginvoyages.dao;

import com.virginvoyages.crossreference.references.Reference;

/**
 * @author snarthu
 *
 */
public interface ReferencesDAO {

public	void addReference(Reference reference);

public Reference findReferenceID(String referenceID);

}
