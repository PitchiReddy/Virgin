package com.virginvoyages.recommendations.api;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.virginvoyages.exception.InvalidSailorIdException;
import com.virginvoyages.recommendations.content.model.ContentRecommendation;
import com.virginvoyages.recommendations.content.model.ContentRecommendations;
import com.virginvoyages.recommendations.exceptions.TribeNotFoundException;
import com.virginvoyages.recommendations.model.RecommendationResponse;
import com.virginvoyages.recommendations.tribes.model.Tribe;
import com.virginvoyages.recommendations.tribes.model.Tribes;

/**
 * Mock implementation of Recommendation API to return mock responses to validate deployment
 */
@Component
public class MockRecommendationAPI {

	private List<RecommendationResponse> recommendationResponses = new ArrayList<RecommendationResponse>();

	private List<ContentRecommendation> contentRecommendationsData = new ArrayList<ContentRecommendation>();
	private final List<Tribe> tribesData = new ArrayList<>();
	@PostConstruct
    void init() {
		addContentRecommendation(new ContentRecommendation()
				.contentTag("Dummy Content Tag 1")
				.contentTagID("1")
				.nbxUniqueKey("123"));
		addContentRecommendation(new ContentRecommendation()
				.contentTag("Dummy Content Tag 2")
				.contentTagID("2")
				.nbxUniqueKey("234"));
		addTribeData(new Tribe().tribe("Explorer"));
		/*addTribeData(new Tribe().tribe("Sailor Dummy Tribe"));
		addTribeData(new Tribe().tribe("Sailor Dummy Tribe").subTribe("Sailor Dummy Sub Tribe"));
		addTribeData(new Tribe().tribe("Explorer"));
		addTribeData(new Tribe().tribe("Sports"));*/
    }

	public void addRecommendationResponse(String nbxUniqueKey, String recommendation, String selectionSentiment) {
    	recommendationResponses
    		.add(new RecommendationResponse()
    				.nbxUniqueKey(nbxUniqueKey)
    				.recommedation(recommendation)
    				.selectionSentiment(selectionSentiment));

    }

    public ContentRecommendations getContentRecommendations(String requestSource,String sailorID,
    		String channel,List<String> place) {
    	return new ContentRecommendations().contentReco(contentRecommendationsData);
    }

    public void addContentRecommendation(ContentRecommendation contentRecommendation) {
    	contentRecommendationsData.add(contentRecommendation);
    }


    public Tribe getTribe(final String requestSource, final String sailorId) throws TribeNotFoundException, InvalidSailorIdException{
    	if(StringUtils.isBlank(sailorId)) {
    		throw new InvalidSailorIdException();
    	}
    	return tribesData.get(0).sailorId(sailorId);
    }

    public Tribes getTribes(final String requestSource, final String sailorId) throws TribeNotFoundException, InvalidSailorIdException{
    	if(StringUtils.isBlank(sailorId)) {
    		throw new InvalidSailorIdException();
    	}
    	return new Tribes().tribe(tribesData.stream().map(tribe -> tribe.sailorId(sailorId)).collect(Collectors.toList()));
    }
    private void addTribeData(final Tribe tribe) {
    	tribesData.add(tribe);
    }

}
