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

  @JsonProperty("nativeSourceIDValue")
  private String nativeSourceIDValue = null;

  @JsonProperty("masterID")
  private String masterID;
  
  @JsonProperty("referenceTypeID")
  private String referenceTypeID = null;
  
  public ReferenceData convertToDataEntity() {
	  return new ReferenceData()
			  .nativeSourceIDValue(this.nativeSourceIDValue())
			  .referenceID(this.referenceID())
			  .masterID(this.masterID())
			  .referenceTypeData(new ReferenceTypeData().referenceTypeID(this.referenceTypeID()));
			  
  }

public ReferenceData convertToUpdateDataEntity(String referenceID) {
	
	 return new ReferenceData()
			  .referenceID(referenceID)
			  .nativeSourceIDValue(nativeSourceIDValue())
			  .masterID(masterID())
			  .referenceTypeData(new ReferenceTypeData().referenceTypeID(referenceTypeID()));
  }

}

