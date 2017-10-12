package com.virginvoyages.crossreference.types;

import java.util.List;
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
  
  
  
  public ReferenceTypeData convertToDataEntity(List<ReferenceSourceData> listOfreferenceSourceData) {
	 
	  ReferenceTypeData referenceTypeData = null;
	  try {
	  referenceTypeData =  new ReferenceTypeData()
			  .referenceType(this.referenceType())
			  .referenceTypeID(this.referenceTypeID())
			  .referenceSourceData(listOfreferenceSourceData.stream().filter(source -> source.referenceSourceID().equals(this.referenceSourceID())).map(source -> source).findAny().get());
	  
	  }
	  catch (Exception e) {
		throw new DataInsertionException("Reference Source Data not available");
	}
	return referenceTypeData;
					 			  
  }

  public ReferenceTypeData convertToUpdateDataEntity(List<ReferenceSourceData> listOfreferenceSourceData) {
	  return new ReferenceTypeData()
			  .referenceTypeID(referenceTypeID)
			  .referenceSourceData(new ReferenceSourceData().referenceSourceID(this.referenceSourceID()))
			  .referenceSourceData(listOfreferenceSourceData.stream().filter(source -> source.referenceSourceID().equals(this.referenceSourceID())).map(source -> source).findAny().get())
			  .referenceType(this.referenceType());
			  
  }

}

