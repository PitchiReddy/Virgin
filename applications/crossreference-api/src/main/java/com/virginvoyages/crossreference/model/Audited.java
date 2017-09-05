package com.virginvoyages.crossreference.model;

import org.joda.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.experimental.Accessors;
/**
 * Audited
 */
@Data
@Accessors(fluent = true, chain = true)
public class Audited   {
  @JsonProperty("createDate")
  private LocalDate createDate = null;

  @JsonProperty("createUser")
  private String createUser = null;

  @JsonProperty("updateDate")
  private LocalDate updateDate = null;

  @JsonProperty("updateUser")
  private String updateUser = null;
 
}

