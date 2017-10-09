package com.virginvoyages.api;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.virginvoyages.recommendations.content.ContentRecommendation;
import com.virginvoyages.recommendations.content.ContentRecommendations;
import com.virginvoyages.recommendations.model.RecommendationResponse;

/**
 * Mock implementation of Recommendation API to return mock responses to validate deployment
 */
@Component
public class MockRecommendationAPI {
	
	private List<RecommendationResponse> recommendationResponses = new ArrayList<RecommendationResponse>();

	private List<ContentRecommendation> contentRecommendationsData = new ArrayList<ContentRecommendation>();
	
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
}
