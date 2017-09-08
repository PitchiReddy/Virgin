package com.virginvoyages.recommendations.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true, chain = true)
public class KeyValue   {
  @JsonProperty("key")
  private String key = null;

  @JsonProperty("value")
  private String value = null;
 
}

