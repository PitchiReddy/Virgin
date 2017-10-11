package com.virginvoyages.recommendations.herenow;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true, chain = true)
public class HereNowRecommendation   {
  	
  @JsonProperty("nbxUniqueKey")
  private String nbxUniqueKey = null;

  @JsonProperty("hereNowItem")
  private String hereNowItem = null;

  @JsonProperty("hereNowItemID")
  private String hereNowItemID = null;
  
}

