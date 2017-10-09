package com.virginvoyages.recommendations.assembly.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.virginvoyages.recommendations.assembly.RecommendationResponseAssembly;
import com.virginvoyages.recommendations.data.repositories.RecommendationResponseRepository;
import com.virginvoyages.recommendations.exceptions.DataInsertionException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RecommendationResponseAssemblyImpl implements RecommendationResponseAssembly {

	@Autowired 
	private RecommendationResponseRepository recommendationResponseRespository;
	
	@Override
	public boolean addRecommendationResponse(String nbxUniqueKey, String sailorSelection, String selectionSentiment) {
		log.debug("Entering addRecommendationResponse");
		try {
			recommendationResponseRespository.save(nbxUniqueKey, sailorSelection, selectionSentiment);
		}catch(Exception ex) {
			log.error("Exception encountered while adding recommendation response ==> ",ex);
			throw new DataInsertionException();
		}
		return true;
	}

}
