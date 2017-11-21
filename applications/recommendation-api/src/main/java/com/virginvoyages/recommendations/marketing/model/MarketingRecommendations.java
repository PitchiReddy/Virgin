package com.virginvoyages.recommendations.marketing.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true, chain = true)
public class MarketingRecommendations   {
  	
  @JsonProperty("marketingReco")
  private List<MarketingRecommendation> marketingReco = new ArrayList<MarketingRecommendation>();
 
}

