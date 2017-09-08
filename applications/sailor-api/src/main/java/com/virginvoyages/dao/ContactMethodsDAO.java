package com.virginvoyages.dao;
/**
 * {@code Interface} for dao tasks for contact methods operations
 * @author pbovilla
 *
 */

import com.virginvoyages.crm.data.ContactMethodsData;
import com.virginvoyages.crm.data.QueryResultsData;

public interface ContactMethodsDAO {

	public QueryResultsData<ContactMethodsData> getContactMethodsForSailor(String sailorID);
}
