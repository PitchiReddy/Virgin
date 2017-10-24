package com.virginvoyages.crossreference.types;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.virginvoyages.data.entities.ReferenceSourceData;
import com.virginvoyages.data.entities.ReferenceTypeData;

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

	public ReferenceTypeData convertToDataEntity() {

		return new ReferenceTypeData()
				.referenceType(this.referenceType())
				.referenceTypeID(this.referenceTypeID())
				.referenceSourceData(new ReferenceSourceData().referenceSourceID(this.referenceSourceID));

	}

}
