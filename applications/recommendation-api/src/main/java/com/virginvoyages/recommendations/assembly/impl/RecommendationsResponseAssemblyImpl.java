package com.virginvoyages.recommendations.assembly.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.virginvoyages.recommendations.assembly.RecommendationsResponseAssembly;
import com.virginvoyages.recommendations.data.repositories.RecommendationResponseRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RecommendationsResponseAssemblyImpl implements RecommendationsResponseAssembly {

	@Autowired 
	private RecommendationResponseRepository recommendationResponseRespository;
	
	@Override
	public void addRecommendationResponse(Integer nbxUniqueKey, String sailorSelection, String selectionSentiment) {
		log.debug("addRecommendationResponse");
		recommendationResponseRespository.save(nbxUniqueKey, sailorSelection, selectionSentiment);
	}

}
