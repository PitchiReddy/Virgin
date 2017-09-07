package com.virginvoyages.recommendations.shipnews;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true, chain = true)
public class ShipNewsRecommendations   {
  	
  @JsonProperty("shipNewsReco")
  private List<ShipNewsRecommendation> shipNewsReco = new ArrayList<ShipNewsRecommendation>();
  
}

