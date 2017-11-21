package com.virginvoyages.assembly.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.virginvoyages.assembly.ContactMethodsAssembly;
import com.virginvoyages.contact.model.ContactMethodsEmbedded;
import com.virginvoyages.crm.data.ContactMethodsData;
import com.virginvoyages.crm.data.QueryResultsData;
import com.virginvoyages.dao.ContactMethodsDAO;

import lombok.extern.slf4j.Slf4j;

/**
 * Implementation for {@link ContactMethodsAssemblyImpl}
 *
 * @author pbovilla
 *
 */
@Service
@Slf4j
public class ContactMethodsAssemblyImpl implements ContactMethodsAssembly {
	
	
	@Autowired
	private ContactMethodsDAO contactMethodsDAO;
	
	 /**
     * Fetches sailor contact methods data based on sailorID. Dummy data being used as of now - as data source not finalized
     * @param sailorID - input sailor id.
     * @return ContactMethodsEmbedded - Contains contact methods data based on record type 
    */
	@Override
	public ContactMethodsEmbedded findSailorsContactMethods(String sailorID) {

		log.debug("Entering findSailorsContactMethods");
		ContactMethodsEmbedded contactMethodsEmbedded = new ContactMethodsEmbedded();

		QueryResultsData<ContactMethodsData> queryResultsData = contactMethodsDAO.getContactMethodsForSailor(sailorID);
		for (ContactMethodsData contactMethodsData : queryResultsData.records()) {
			if ("phone".equals(contactMethodsData.recordTypeId())) {
				contactMethodsEmbedded.addContactMethod(contactMethodsData.convertToContactPhone());
			} else if ("email".equals(contactMethodsData.recordTypeId())) {
				contactMethodsEmbedded.addContactMethod(contactMethodsData.convertToContactEmail());
			} else if ("address".equals(contactMethodsData.recordTypeId())) {
				contactMethodsEmbedded.addContactMethod(contactMethodsData.convertToContactAddress());
			}
		}
		return contactMethodsEmbedded;
	}

}
