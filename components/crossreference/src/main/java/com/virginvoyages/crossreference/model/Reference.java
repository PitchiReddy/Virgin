package com.virginvoyages.crossreference.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.experimental.Accessors;
/**
 * Core entity to store the cross references for the enterprise.
 */
@Data
@Accessors(fluent = true, chain = true)
public class Reference   {
	
  @JsonProperty("referenceID")
  private String referenceID = null;

  @JsonProperty("nativeSourceIDValue")
  private String nativeSourceIDValue = null;

  @JsonProperty("masterID")
  private String masterID;
  
  @JsonProperty("referenceTypeID")
  private String referenceTypeID = null;
  
  //Only for input to search by type and target type operation
  @JsonProperty("targetReferenceTypeID")
  private String targetReferenceTypeID = null;
  

}

