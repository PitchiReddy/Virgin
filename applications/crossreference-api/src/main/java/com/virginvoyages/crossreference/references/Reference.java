package com.virginvoyages.crossreference.references;

import org.joda.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.virginvoyages.crossreference.model.Audited;
import com.virginvoyages.crossreference.sources.ReferenceSource;
import com.virginvoyages.crossreference.types.ReferenceType;

import lombok.Data;
import lombok.experimental.Accessors;
/**
 * Core entity to store the cross references for the enterprise.
 */
@Data
@Accessors(fluent = true, chain = true)
public class Reference   {
	
  @JsonProperty("auditData")	
  private Audited auditData = null;	

  @JsonProperty("referenceSource")
  private ReferenceSource referenceSource = null;

  @JsonProperty("referenceType")
  private ReferenceType referenceType = null;

  @JsonProperty("referenceID")
  private String referenceID = null;

  @JsonProperty("nativeSourceID")
  private String nativeSourceID = null;

  @JsonProperty("masterID")
  private String masterID = null;

  @JsonProperty("expiry")
  private LocalDate expiry = null;

  @JsonProperty("details")
  private String details = null;

}

