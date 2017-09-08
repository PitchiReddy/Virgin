package com.virginvoyages.recommendations.calendar;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true, chain = true)
public class CalenderRecommendation   {
	
  @JsonProperty("nbxUniqueKey")
  private Integer nbxUniqueKey = null;

  @JsonProperty("calenderRecoItem")
  private String calenderRecoItem = null;

  @JsonProperty("calenderRecoID")
  private Integer calenderRecoID = null;
  
}

