package com.virginvoyages.crm.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.virginvoyages.crossreference.client.Reference;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true, chain = true)
public class ReferenceData {

	@JsonProperty("masterID")
	private String masterID;

    @JsonProperty("nativeSourceIDValue")
    private String nativeSourceIDValue;
    
	@JsonProperty("referenceID")
	private String referenceID;
	
	@JsonProperty("referenceTypeID")
	private String referenceTypeID;
	
	public Reference convertToReferenceObject(ReferenceData referenceData) {
		Reference reference = new Reference();
		reference.masterID(referenceData.masterID());
		reference.nativeSourceIDValue(referenceData.nativeSourceIDValue());
		reference.referenceTypeID(referenceData.referenceTypeID());
		return reference;
	}

}
