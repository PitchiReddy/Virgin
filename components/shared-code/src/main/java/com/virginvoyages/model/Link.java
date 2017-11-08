package com.virginvoyages.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.experimental.Accessors;
/**
 * Link
 */
@Data
@Accessors(fluent = true, chain = true)
public class Link   {
  @JsonProperty("href")
  private String href = null;

  @JsonProperty("rel")
  private String rel = null;

  @JsonProperty("templated")
  private Boolean templated = null;

}

