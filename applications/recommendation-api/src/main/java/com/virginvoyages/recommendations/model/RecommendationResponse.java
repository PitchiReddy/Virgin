package com.virginvoyages.recommendations.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true, chain = true)
public class RecommendationResponse {

	@JsonProperty("nbxUniqueKey")
	private String nbxUniqueKey = null;

	@JsonProperty("recommedation")
	private String recommedation = null;

	@JsonProperty("recommedationID")
	private String recommedationID = null;
	
	@JsonProperty("selectionSentiment")
	private String selectionSentiment = null;
	
	
}
