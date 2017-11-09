package com.virginvoyages.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.experimental.Accessors;
/**
 * Page
 */
@Data
@Accessors(fluent = true, chain = true)
public class Page   {
  @JsonProperty("size")
  private Integer size = null;

  @JsonProperty("totalElements")
  private Integer totalElements = null;

  @JsonProperty("totalPages")
  private Integer totalPages = null;

  @JsonProperty("number")
  private Integer number = null;
  
}

