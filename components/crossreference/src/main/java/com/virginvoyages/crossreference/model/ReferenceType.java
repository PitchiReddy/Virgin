package com.virginvoyages.model.crossreference;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Entity that represents the type and name of the entity for the referenced SOR
 */
@Data
@Accessors(fluent = true, chain = true)
public class ReferenceType {

	@JsonProperty("referenceTypeID")
	private String referenceTypeID = null;

	@JsonProperty("referenceType")
	private String referenceType = null;

	@JsonProperty("referenceSourceID")
	private String referenceSourceID = null;

}
