package com.virginvoyages.recommendations.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true, chain = true)
public class Recommendation   {
	
  @JsonProperty("reco")
  private List<KeyValue> reco = new ArrayList<KeyValue>();
}

