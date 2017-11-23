package com.virginvoyages.recommendations.content.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true, chain = true)
public class ContentRecommendations   {
	
  @JsonProperty("contentReco")
  private List<ContentRecommendation> contentReco = new ArrayList<ContentRecommendation>();
  
  public ContentRecommendations addContentRecommendation(ContentRecommendation contentRecommendaton) {
	  contentReco.add(contentRecommendaton);
	  return this;
  }
  
}

