package com.virginvoyages.recommendations.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true, chain = true)
public class RecommendationData {

	@JsonProperty("nbxUniqueKey")
	private Integer nbxUniqueKey = null;

	@JsonProperty("recommedation")
	private String recommedation = null;

	@JsonProperty("recommedationID")
	private Integer recommedationID = null;
	
	@JsonProperty("selectionSentiment")
	private String selectionSentiment = null;
	
	
}
