package com.virginvoyages.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.virginvoyages.crm.data.ContactMethodsData;
import com.virginvoyages.crm.data.QueryResultsData;
import com.virginvoyages.dao.ContactMethodsDAO;

import lombok.extern.slf4j.Slf4j;
/**
 * Data access layer for Contact Methods.
 * Stubbed out now as data source yet to be finalized
 * @author rpraveen 
 */

@Service
@Slf4j
public class ContactMethodsDAOImpl implements ContactMethodsDAO {
	
	/**
	 * Mock implementation.
	 */
	@Override
	public QueryResultsData<ContactMethodsData> getContactMethodsForSailor(String sailorID) {
		
		log.debug("Entering findSailorsContactMethods");
		
		QueryResultsData<ContactMethodsData> queryResultsData = new QueryResultsData<ContactMethodsData>();
		List<ContactMethodsData> contactMethodsDataList = new ArrayList<ContactMethodsData>();
		contactMethodsDataList.add(createContactMethodsData("phone"));
		contactMethodsDataList.add(createContactMethodsData("email"));
		contactMethodsDataList.add(createContactMethodsData("address"));
		
		queryResultsData.records(contactMethodsDataList);
		return queryResultsData;
	}

	//TODO remove once data source finalized
	/**
	 *
	 * Temporary method - will be removed
	 * @param contactMethodType
	 * @return
	 */
	private ContactMethodsData createContactMethodsData(String contactMethodType) {
		ContactMethodsData contactMethodsData = new ContactMethodsData();

		contactMethodsData.addressLine1("Line 1");
		contactMethodsData.addressLine2("Line 2");
		contactMethodsData.addressLine3("Line 3");
		contactMethodsData.addressLine4("Line 4");
		contactMethodsData.city("Plantation");
		contactMethodsData.state("FL");
		contactMethodsData.province("Plant");
		contactMethodsData.countryCode("US");
		contactMethodsData.postalCode("90001");
		contactMethodsData.country("United States");
		contactMethodsData.email("sailor@virgin.com");
		contactMethodsData.phoneNumber("999-888-7777");
		contactMethodsData.extenstion("x1243");
		contactMethodsData.recordTypeId(contactMethodType);
		
		return contactMethodsData;
	}
}
