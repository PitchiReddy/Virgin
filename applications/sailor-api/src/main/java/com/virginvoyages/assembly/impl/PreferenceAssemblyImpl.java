package com.virginvoyages.assembly.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.virginvoyages.assembly.PreferenceAssembly;
import com.virginvoyages.crm.client.QueryClient;
import com.virginvoyages.crm.data.PreferenceData;
import com.virginvoyages.crm.data.QueryResultsData;
import com.virginvoyages.preference.model.PreferencesEmbedded;
import com.virginvoyages.sailor.helper.SailorQueryHelper;

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
	
	@Autowired
	private SailorQueryHelper sailorQueryHelper;

	@Override
	public PreferencesEmbedded findSailorPreferences(String sailorID) {
		
		PreferencesEmbedded preferencesEmbedded = new PreferencesEmbedded();
		
		try {
			QueryResultsData<PreferenceData> queryResultsData = queryClient.getSailorPreferences(
					sailorQueryHelper.generateGetSailorPreferencesQuery(sailorID));
			for (PreferenceData preferenceData : queryResultsData.records()) {
				preferencesEmbedded.addPreferencesItem(preferenceData.convertToPreferenceObject());
			}
				
		} catch (FeignException fe) {
			if (HttpStatus.BAD_REQUEST.value() == fe.status()) {
				log.debug("Invalid sailorID used ==> "+sailorID);
			}
			log.error("FeignException encountered for sailor ==>  "+sailorID,fe);
		} catch (Exception ex) {
			log.error("Unknown Exception encountered in findSailorPreferences for sailor ==> "+sailorID,ex);
		}
		return preferencesEmbedded;
	}
}
