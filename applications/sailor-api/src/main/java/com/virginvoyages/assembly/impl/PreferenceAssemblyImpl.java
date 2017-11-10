package com.virginvoyages.assembly.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.virginvoyages.assembly.PreferenceAssembly;
import com.virginvoyages.crm.client.QueryClient;
import com.virginvoyages.crm.data.PreferenceData;
import com.virginvoyages.crm.data.QueryResultsData;
import com.virginvoyages.preference.PreferencesEmbedded;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation for {@link PreferenceAssembly}
 * 
 * @author rpraveen
 *
 */
@Service
@Slf4j
public class PreferenceAssemblyImpl implements PreferenceAssembly {

	@Autowired
	private QueryClient queryClient;

	@Override
	public PreferencesEmbedded findSailorPreferences(String sailorID) {

		String query = "SELECT Id,Category__c,Preference_Options__c,Sailor_ID__c,Sub_Category__c FROM Sailor_Preference__c WHERE Sailor_ID__c = '"
				+ sailorID + "'";
		
		PreferencesEmbedded preferencesEmbedded = new PreferencesEmbedded();
		
		try {
			QueryResultsData<PreferenceData> queryResultsData = queryClient.getSailorPreferences(query);
			for (PreferenceData preferenceData : queryResultsData.records()) {
				preferencesEmbedded.addPreferencesItem(preferenceData.convertToPreferenceObject());
			}
			
		} catch (FeignException fe) {
			/*if (HttpStatus.BAD_REQUEST.value() == fe.status()) {
				throw new InvalidQueryFilterException();
			}*/
			log.error("FeignException encountered - to be handled ",fe.getMessage());
		}
		return preferencesEmbedded;
	}
}
