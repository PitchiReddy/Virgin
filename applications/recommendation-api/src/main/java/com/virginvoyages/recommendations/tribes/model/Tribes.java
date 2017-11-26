package com.virginvoyages.recommendations.tribes.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true, chain = true)
public class Tribes   {
  	
  @JsonProperty("tribe")
  private List<Tribe> tribe = new ArrayList<Tribe>();

}

