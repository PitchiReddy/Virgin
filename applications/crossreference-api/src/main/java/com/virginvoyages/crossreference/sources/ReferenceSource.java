package com.virginvoyages.crossreference.sources;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.virginvoyages.crossreference.model.Audited;

import lombok.Data;
import lombok.experimental.Accessors;
/**
 * Entity that represents the System of Record (SOR)
 */

@Data
@Accessors(fluent = true, chain = true)
public class ReferenceSource   {
  @JsonProperty("auditData")	
  private Audited auditData = null;	

  @JsonProperty("referenceSourceID")
  private String referenceSourceID = null;

  @JsonProperty("referenceSourceName")
  private String referenceSourceName = null;

  @JsonProperty("inActive")
  private Boolean inActive = null;

 }

