package com.virginvoyages.recommendations.marketing;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true, chain = true)
public class MarketingRecommendation   {
	
  @JsonProperty("nbxUniqueKey")
  private String nbxUniqueKey = null;

  @JsonProperty("marketingTag")
  private String marketingTag = null;

  @JsonProperty("marketingTagID")
  private String marketingTagID = null;
  
}

