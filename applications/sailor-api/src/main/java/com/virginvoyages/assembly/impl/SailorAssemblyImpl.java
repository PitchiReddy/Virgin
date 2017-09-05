package com.virginvoyages.assembly.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.virginvoyages.assembly.SailorAssembly;
import com.virginvoyages.crm.client.AccountClient;
import com.virginvoyages.crm.client.QueryClient;
import com.virginvoyages.crm.data.AccountCreateStatus;
import com.virginvoyages.crm.data.AccountData;
import com.virginvoyages.crm.data.QueryResultsData;
import com.virginvoyages.crm.data.RecordTypeData;
import com.virginvoyages.sailor.Sailor;
import com.virginvoyages.sailor.SailorMapper;
import com.virginvoyages.sailor.exceptions.AccountCreationException;
import com.virginvoyages.sailor.exceptions.DataNotFoundException;
import com.virginvoyages.sailor.helper.SailorQueryHelper;

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
    
	@Override
	public Sailor getSailorById(String sailorID) {
		AccountData accountData = new AccountData();
		try {
			accountData = accountClient.findAccount(sailorID);
		} catch (FeignException fe) {
			if (HttpStatus.NOT_FOUND.value() == fe.status()) {
				throw new DataNotFoundException();
			}
			log.error("FeignException encountered - to be handled ",fe.getMessage());
		}
		return accountData.convertToSailorObject();

	}

	@Override
	public void deleteSailorById(String sailorID) {
		// TODO what is the error handling mechanism for errors at CRM end
		log.debug("deleting sailor details - SaiorById");
		try {
			accountClient.deleteAccount(sailorID);
		} catch (FeignException fe) {
			if (HttpStatus.NOT_FOUND.value() == fe.status()) {
				throw new DataNotFoundException();
			}
			log.error("FeignException encountered - to be handled",fe.getMessage());
		}
		
	}

	@Override
	public List<Sailor> findSailors(AccountData accountData) {
		
		//TODO what is the error handling mechanism for errors at CRM end
		String findQueryString = sailorQueryHelper.generateFindQueryString(accountData);
		QueryResultsData<AccountData> queryResultsData = queryClient.findAccounts(findQueryString);
		
		List<String> listOfSailorIDs = sailorMapper.retrieveListOfSailorIDs(queryResultsData);
		log.debug("Request to return list of sailor's with ID {}", listOfSailorIDs);
				
		// TODO fix multi thread issue with oauth2ClientContext and change to parallelstream
		return listOfSailorIDs.stream().map(sailorID  -> {
            return accountClient.findAccount(sailorID).convertToSailorObject() ;
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
		
		return accountClient.findAccount(sailorID).convertToSailorObject();
		
 	}

}
