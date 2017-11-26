package com.virginvoyages.recommendations.content.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true, chain = true)
public class ContentRecommendation   {
	
  @JsonProperty("nbxUniqueKey")
  private String nbxUniqueKey = null;

  @JsonProperty("contentTag")
  private String contentTag = null;

  @JsonProperty("contentTagID")
  private String contentTagID = null;
  
}

