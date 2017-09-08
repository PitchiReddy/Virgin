package com.virginvoyages.recommendations.calendar;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true, chain = true)
public class CalendarRecommendations   {
  	
  @JsonProperty("calenderReco")
  private List<CalenderRecommendation> calenderReco = new ArrayList<CalenderRecommendation>();
  
}

