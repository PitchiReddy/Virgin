package com.virginvoyages.crossreference.types;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.virginvoyages.crossreference.model.Audited;
import com.virginvoyages.data.entities.ReferenceTypeData;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Entity that represents the type and name of the entity for the referenced SOR
 */
@Data
@Accessors(fluent = true, chain = true)
public class ReferenceType   {
	
  @JsonProperty("auditData")	
  private Audited auditData = null;	
 
  @JsonProperty("referenceTypeID")
  private String referenceTypeID = null;

  @JsonProperty("referenceName")
  private String referenceName = null;

  @JsonProperty("referenceType")
  private String referenceType = null;
  
  public ReferenceTypeData convertToDataEntity() {
	  return new ReferenceTypeData()
			  .referenceName(this.referenceName())
			  .referenceType(this.referenceType())
			  .createDate(this.auditData().createDate().toString())
			  .createUser(this.auditData().createUser())
			  .updateDate(this.auditData().updateDate().toString())
			  .updateUser(this.auditData().updateUser());
  }

}

