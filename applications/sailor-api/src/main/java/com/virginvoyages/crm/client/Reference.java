package com.virginvoyages.crm.client;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
/**
 * Core entity to store the cross references for the enterprise.
 */
@Data
@Accessors(fluent = true, chain = true)
public class Reference  implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty("referenceID")
	private String referenceID = null;

	@JsonProperty("nativeSourceIDValue")
	private String nativeSourceIDValue = null;

	@JsonProperty("masterID")
	private String masterID;

	@JsonProperty("referenceTypeID")
	private String referenceTypeID = null;

	@JsonProperty("errors")
	private List<String> errors = new ArrayList<String>();
	
	public Reference convertToBusinessEntity() {
		return new Reference()
				.referenceID(String.valueOf(this.referenceID()))
				.nativeSourceIDValue(String.valueOf(this.nativeSourceIDValue()))
				.masterID(String.valueOf(this.masterID()))
				.referenceTypeID(String.valueOf(this.referenceTypeID()));

	}
	
}

