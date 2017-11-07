package com.virginvoyages.crm.client;


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
  
  public Reference convertToBusinessEntity() {
		return new Reference()
				.referenceID(String.valueOf(this.referenceID()))
				.nativeSourceIDValue(String.valueOf(this.nativeSourceIDValue()))
				.masterID(String.valueOf(this.masterID()))
				.referenceTypeID(String.valueOf(this.referenceTypeID()));
				
				
	}
	
}

