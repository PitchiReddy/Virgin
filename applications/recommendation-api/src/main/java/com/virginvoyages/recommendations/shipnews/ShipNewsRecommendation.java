package com.virginvoyages.recommendations.shipnews;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true, chain = true)
public class ShipNewsRecommendation   {
	
  @JsonProperty("nbxUniqueKey")
  private String nbxUniqueKey = null;

  @JsonProperty("recommedation")
  private String recommedation = null;

  @JsonProperty("recommedationID")
  private String recommedationID = null;
  
}

