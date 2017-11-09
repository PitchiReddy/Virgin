package com.virginvoyages.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.experimental.Accessors;
/**
 * Links
 */
@Data
@Accessors(fluent = true, chain = true)
public class Links   {
  @JsonProperty("first")
  private Link first = null;

  @JsonProperty("prev")
  private Link prev = null;

  @JsonProperty("self")
  private Link self = null;

  @JsonProperty("next")
  private Link next = null;

  @JsonProperty("last")
  private Link last = null;

  @JsonProperty("profile")
  private Link profile = null;

}

