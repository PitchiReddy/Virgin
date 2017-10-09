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
public class ReferenceType   {

  @JsonProperty("referenceTypeID")
  private String referenceTypeID = null;

  @JsonProperty("referenceType")
  private String referenceType = null;
  
  @JsonProperty("referenceSourceID")
  private String referenceSourceID = null;
  
  public ReferenceTypeData convertToDataEntity() {
	  return new ReferenceTypeData()
			  .referenceType(this.referenceType())
			  .referenceTypeID(Long.parseLong(this.referenceTypeID()))
			  .referenceSourceData(new ReferenceSourceData().referenceSourceID(Long.parseLong(this.referenceSourceID())).referenceSourceName(this.referenceType()));
			  
  }

  public ReferenceTypeData convertToUpdateDataEntity(String referenceTypeID) {
	  return new ReferenceTypeData()
			  .referenceTypeID(Long.parseLong(referenceTypeID))
			  .referenceSourceData(new ReferenceSourceData().referenceSourceID(Long.parseLong(this.referenceSourceID())).referenceSourceName(this.referenceType()))
			  .referenceType(this.referenceType());
			  
}

}

