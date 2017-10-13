package com.virginvoyages.crossreference.types;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.virginvoyages.crossreference.exceptions.DataInsertionException;
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
	 
	  ReferenceTypeData referenceTypeData = null;
	  try {
	  referenceTypeData =  new ReferenceTypeData()
			  .referenceType(this.referenceType())
			  .referenceTypeID(this.referenceTypeID())
			  .referenceSourceData(new ReferenceSourceData().referenceSourceID(this.referenceSourceID));
	  
	  }
	  catch (Exception e) {
		throw new DataInsertionException("Reference Source Data not available");
	}
	return referenceTypeData;
					 			  
  }

  public ReferenceTypeData convertToUpdateDataEntity(String referenceTypeID) {
	  return new ReferenceTypeData()
			  .referenceTypeID(referenceTypeID)
			  .referenceSourceData(new ReferenceSourceData().referenceSourceID(referenceSourceID()))
			  .referenceType(this.referenceType());
			  
  }

}

