package com.virginvoyages.recommendations.tribes.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.virginvoyages.recommendations.model.KeyValue;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true, chain = true)
public class Tribe   {
	
  @JsonProperty("sailorId")
  private String sailorId = null;

  @JsonProperty("tribe")
  private String tribe = null;

  @JsonProperty("subTribe")
  private String subTribe = null;

  @JsonProperty("sailorAttributes")
  private List<KeyValue> sailorAttributes = new ArrayList<KeyValue>();

  @JsonProperty("stateOfSailor")
  private List<KeyValue> stateOfSailor = new ArrayList<KeyValue>();
  
}

