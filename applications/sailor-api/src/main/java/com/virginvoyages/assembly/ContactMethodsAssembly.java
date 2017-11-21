package com.virginvoyages.assembly;

import com.virginvoyages.contact.model.ContactMethodsEmbedded;


/**
 * {@code Interface} for assembly tasks for contact methods operations
 * @author pbovilla
 *
 */
public interface ContactMethodsAssembly {

	/**
	 * Find sailor contact methods based on sailorID
	 * @param sailorID 
	 * @return
	 */
	public ContactMethodsEmbedded findSailorsContactMethods(String sailorID);
}
