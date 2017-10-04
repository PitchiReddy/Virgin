package com.virginvoyages.recommendations.assembly;

/**
 * {@code Interface} for assembly tasks for recommendation response
 * 
 * @author rpraveen
 *
 */
public interface RecommendationResponseAssembly {
	
	public boolean addRecommendationResponse(Integer nbxUniqueKey, String sailorSelection, String selectionSentiment);

}
