package com.virginvoyages.sailor.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.virginvoyages.crm.data.AccountData;
import com.virginvoyages.crm.data.QueryResultsData;
import com.virginvoyages.sailor.model.Sailor;
import com.virginvoyages.seaware.data.ClientData;

import lombok.extern.slf4j.Slf4j;

/**
 * SailorMapper class to map account data to sailor
 * @author snarthu
 *
 */

@Component
@Slf4j
public class SailorMapper {

	/**
	* Map account data to sailor 
	* @Param accountData - values from accountData object are mapped to sailor object 
	* @return Sailor - Sailor object that contains data from accountData.
	*/
	public static Sailor mapAccountDataToSailor(AccountData accountData) {
		Sailor sailor = new Sailor();

		log.debug("Mapping CRM Account Data to Sailor");
		sailor.id(accountData.id());
		sailor.firstName(accountData.firstName());
		sailor.middleName(accountData.middleName());
		sailor.lastName(accountData.lastName());
		sailor.prefix(accountData.salutation());
		sailor.suffix(accountData.suffix());
		sailor.nickname(accountData.preferredName());
		sailor.gender(accountData.gender());
		sailor.occupation(accountData.occupation());
		sailor.citizenshipCountry(accountData.citizenshipCountry());
		sailor.preferredLanguage(accountData.preferredLanguage());
		sailor.dateofBirth(accountData.dateofBirth());
		sailor.birthCountry(accountData.birthCountry());
		sailor.ageGroup(accountData.ageGroup());
		sailor.retirementDate(accountData.retirementDate());
		sailor.martialStatus(accountData.martialStatus());
		sailor.numberofChildren(accountData.numberofChildren());
		sailor.totalLifetimeCruiseNights(accountData.totalLifetimeCruiseNights());
		sailor.tribe(accountData.tribe());
		sailor.status(accountData.status());
		sailor.subTribe(accountData.subTribe());
		sailor.stateOfTheSailor(accountData.stateOfTheSailor());
		sailor.vIP(accountData.vIP());
		sailor.averageNTRAmount(accountData.averageLifetimeSpendPerNight());
		sailor.averageOBSAmount(accountData.averageOnboardSpendPerNight());
		sailor.mobileNumber(accountData.mobileNumber());
		sailor.primaryEmail(accountData.primaryEmail());
		sailor.salutation(accountData.salutation());
		sailor.recordTypeId(accountData.recordTypeId());
	
		return sailor;
	}
	
	public static Sailor mapClientDataToSailor(ClientData clientData) {
		log.debug("Mapping Seaware Client Data to Sailor");
		Sailor sailor = new Sailor();
		sailor.seawareClientID(clientData.id());
		sailor.firstName(clientData.firstName());
		sailor.middleName(clientData.middleName());
		sailor.lastName(clientData.lastName());
		sailor.prefix(clientData.salutation());
		sailor.suffix(clientData.suffix());
		return null;
	}
		
	/**
	 * Retrieves a list of sailorIDs from list of accountData objects
	 * @param queryResultsData- contains the list of accountData objects.
	 * @return listSailorIds
	 */
	public List<String> retrieveListOfSailorIDs(QueryResultsData<AccountData> queryResultsData) {
		List<String> listSailorIds = new ArrayList<String>();

		List<AccountData> accountsList = queryResultsData.records();
		if (accountsList != null && accountsList.size() > 0) {
			listSailorIds = accountsList.stream().map(accountData -> accountData.id()).collect(Collectors.toList());
		}
		log.debug("list of sailor" + listSailorIds.toString());
		return listSailorIds;
	}
}
