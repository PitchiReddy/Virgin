package com.virginvoyages.sailor.helper;

import org.joda.time.LocalDate;
import org.springframework.stereotype.Component;

import com.virginvoyages.crm.data.AccountData;

import lombok.extern.slf4j.Slf4j;

/**
* SailorQueryHelper class to handle the query .
* @author snarthu
*
*/
@Slf4j
@Component
public class SailorQueryHelper {

	/**
	 * Generates query string based on values in accountData. As of now only works for 5 fields - firstNAme, lastName
	 * ,email,dob and mobileNumber. To be worked on to make more flexible.
	 * @param accountData
	 * @return queryString
	 */
	public String generateFindQueryString(AccountData accountData) {

		// TODO Can be optimized to be more flexible - currently only works for the 5 fields.
		
		StringBuffer queryString = new StringBuffer();
		queryString.append("SELECT Id FROM Account WHERE ");
		if (accountData.firstName() != null) {
			queryString.append("FirstName  = '");
			queryString.append(accountData.firstName());
			queryString.append("'");
		}
		if (accountData.lastName() != null) {
			queryString = accountData.firstName() != null ? queryString.append(" and ") : queryString.append(" ");
			queryString.append("LastName = '");
			queryString.append(accountData.lastName());
			queryString.append("'");
		}
		if (accountData.dateofBirth() != null) {
			queryString = (accountData.lastName() != null || accountData.firstName() != null) ? queryString.append(" and ")
					: queryString.append(" ");
			queryString.append(" DOB__pc =  ");
			queryString.append(accountData.dateofBirth());
		}
		if (accountData.primaryEmail() != null) {
			queryString = (accountData.dateofBirth() != null || accountData.firstName() != null || accountData.lastName() != null) ? queryString.append(" and ")
					: queryString.append(" ");
			queryString.append("Primary_Contact_Email__c= '");
			queryString.append(accountData.primaryEmail());
			queryString.append("'");
		}
		if (accountData.mobileNumber() != null) {
			queryString = (accountData.primaryEmail() != null || accountData.dateofBirth() != null || accountData.firstName() != null || accountData.lastName() != null)
					? queryString.append(" and ")
					: queryString.append(" ");
			queryString.append("PersonMobilePhone = '");
			queryString.append(accountData.mobileNumber());
			queryString.append("'");
		}
		log.debug("Request to return create sailor QueryString {}", queryString.toString());

		String query = queryString.toString();
		return query;

	}

}
