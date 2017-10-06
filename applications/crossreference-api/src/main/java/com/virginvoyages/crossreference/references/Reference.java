package com.virginvoyages.crossreference.references;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.virginvoyages.data.entities.ReferenceData;
import com.virginvoyages.data.entities.ReferenceTypeData;

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

  @JsonProperty("nativeSourceID")
  private String nativeSourceID = null;

  @JsonProperty("masterID")
  private String masterID;
  
  @JsonProperty("referenceTypeID")
  private String referenceTypeID = null;
  
  public ReferenceData convertToDataEntity() {
	  return new ReferenceData()
			  .nativeSourceID(this.nativeSourceID())
			  .masterID(this.masterID())
			  .referenceTypeData(new ReferenceTypeData().referenceTypeID(Long.parseLong(this.referenceTypeID())));
			  
  }

}

