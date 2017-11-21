package com.virginvoyages.recommendations.herenow.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.experimental.Accessors;
@Data
@Accessors(fluent = true, chain = true)
public class HereNowRecommendations   {
	
  @JsonProperty("hereNowReco")
  private List<HereNowRecommendation> hereNowReco = new ArrayList<HereNowRecommendation>();

}

