package com.virginvoyages.assembly.impl;

import static com.virginvoyages.crossreference.constants.CrossReferenceConstants.REFERENCE_TYPE_CLIENT;
import static com.virginvoyages.crossreference.constants.CrossReferenceConstants.REFERENCE_TYPE_PERSON;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.virginvoyages.assembly.PreferenceAssembly;
import com.virginvoyages.assembly.SailorAssembly;
import com.virginvoyages.booking.model.BookingsEmbedded;
import com.virginvoyages.crm.client.AccountClient;
import com.virginvoyages.crm.client.QueryClient;
import com.virginvoyages.crm.data.AccountCreateStatus;
import com.virginvoyages.crm.data.AccountData;
import com.virginvoyages.crm.data.QueryResultsData;
import com.virginvoyages.crm.data.RecordTypeData;
import com.virginvoyages.exception.DataNotFoundException;
import com.virginvoyages.exception.UnknownException;
import com.virginvoyages.preference.model.PreferencesEmbedded;
import com.virginvoyages.sailor.exceptions.AccountCreationException;
import com.virginvoyages.sailor.helper.SailorMapper;
import com.virginvoyages.sailor.helper.SailorQueryHelper;
import com.virginvoyages.sailor.model.Sailor;
import com.virginvoyages.seaware.dao.SeawareDAO;
import com.virginvoyages.seaware.data.ClientData;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation for {@link SailorAssembly}
 * @author rpraveen
 *
 */
@Service
@Slf4j
public class SailorAssemblyImpl implements SailorAssembly {

	@Autowired
    private AccountClient accountClient;

	@Autowired
	private QueryClient queryClient;

	@Autowired
	private SailorQueryHelper sailorQueryHelper;

	@Autowired
	private SailorMapper sailorMapper;

	@Autowired
	private PreferenceAssembly preferenceAssembly;

	@Autowired
	private SeawareDAO seawareDAO;

	@Override
	public Sailor getSailorById(String sailorID) {
		AccountData accountData;
		try {
			accountData = accountClient.findAccount(sailorID);
		} catch (FeignException fe) {
			if (HttpStatus.NOT_FOUND.value() == fe.status()) {
				throw new DataNotFoundException();
			}
			log.error("FeignException encountered - to be handled ",fe.getMessage());
			throw new UnknownException();
		}
		return convertAccountDataToSailor(accountData, loadPreferences(sailorID), null);

	}

	@Override
	public void deleteSailorById(String sailorID) {
		try {
			accountClient.deleteAccount(sailorID);
		} catch (FeignException fe) {
			if (HttpStatus.NOT_FOUND.value() == fe.status()) {
				throw new DataNotFoundException();
			}
			log.error("FeignException encountered - to be handled ",fe.getMessage());
			throw new UnknownException();
		}

	}

	@Override
	public List<Sailor> findSailors(AccountData accountData) {

		String findQueryString = sailorQueryHelper.generateFindQueryString(accountData);
		QueryResultsData<AccountData> queryResultsData = queryClient.findAccounts(findQueryString);

		List<String> listOfSailorIDs = sailorMapper.retrieveListOfSailorIDs(queryResultsData);
		log.debug("Request to return list of sailor's with ID {}", listOfSailorIDs);

		// TODO fix multi thread issue with oauth2ClientContext and change to parallelstream
		return listOfSailorIDs.stream().map(sailorID  -> {
            return convertAccountDataToSailor(accountClient.findAccount(sailorID),loadPreferences(sailorID),null) ;
		}).collect(Collectors.toList());
	}

	@Override
	public Sailor createSailor(AccountData accountData) {

		String sailorID = null;
		RecordTypeData recordTypeData = queryClient.findSailorAccountRecordTypeID().first();
		String recordTypeId = null != recordTypeData ? recordTypeData.id() : null;
		if(StringUtils.isNotEmpty(recordTypeId)) {
			accountData.recordTypeId(recordTypeId);
		}
		try {
			AccountCreateStatus status = accountClient.createAccount(accountData);
			if(status.successStatus() != "true") {
				log.debug("Account creation failed at CRM end");
				throw new AccountCreationException();
			}
			sailorID = status.id();
		} catch (FeignException fe) {
			log.error("FeignException encountered during account create ",fe.getMessage());
			throw new AccountCreationException();
		}

		return convertAccountDataToSailor(accountClient.findAccount(sailorID),loadPreferences(sailorID),null);

 	}

	private PreferencesEmbedded loadPreferences(String sailorID) {
		return preferenceAssembly.findSailorPreferences(sailorID);
	}

	private Sailor convertAccountDataToSailor(AccountData accountData, PreferencesEmbedded preferencesEmbedded, BookingsEmbedded bookingsEmbedded) {
		return accountData.convertToSailorObject()
				.associatePreferences(preferencesEmbedded)
				.associateSailingHistory(bookingsEmbedded);
	}

	public Sailor getOrchestratedSailorData(String salesforceID) {
		AccountData salesforceData = getSalesforceAccountData(salesforceID);
		ClientData seawareClientData = getSeawareClientData(getSeawareClientIDForSalesforceID(salesforceID));
		if(null == seawareClientData && null != salesforceData) {
			//If not in seaware use salesforceData
			return convertAccountDataToSailor(salesforceData, loadPreferences(salesforceData.id()), null);
		}
		//Replace with logic to merge data between salesforce and seaware. - It should be a combination of seaware and salesforce data
		return new Sailor();
	}

	public String getSeawareClientIDForSalesforceID(String salesforceID) {
		return getTargetRecordID(salesforceID,
									getReferenceTypeIDForName(REFERENCE_TYPE_PERSON),
									getReferenceTypeIDForName(REFERENCE_TYPE_CLIENT));
	}

	/**
	 *
	 * @param referenceTypeName - ReferenceType name whose ID is
	 * @return
	 */
	public String getReferenceTypeIDForName(String referenceTypeName) {
		String referenceTypeID = null;
		// Call CrossReference -> Types - > findbyname -> name = referenceTypeName
		return referenceTypeID;
	}

	public String getTargetRecordID(String sourceRecordID,String sourceTypeID, String targetTypeID) {
		return "dummy";
		//Reference reference = null;
		/*For reference Call CrossReference -> References - > findbytypeandtargetType - >
		nativesourceid = sourcerecordid
		typeid = sourcetypeid
		targettypeid = targettypeid
		*/
		//return reference != null ? reference.nativeSourceIDValue() : null;
	}

	public AccountData getSalesforceAccountData(String sailorID) {
		try {
			return accountClient.findAccount(sailorID);
		} catch (Exception fe) {
			log.error("Exception encountered - to be handled ",fe);
			return null;
		}
	}

	public ClientData getSeawareClientData(String seawareClientID) {
		return StringUtils.isNotEmpty(seawareClientID) ? seawareDAO.getSeawareClientData(seawareClientID) : null;
	}

}
