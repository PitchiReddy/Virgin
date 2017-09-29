package com.virginvoyages.crossreference.references;

import org.joda.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.virginvoyages.crossreference.model.Audited;
import com.virginvoyages.crossreference.sources.ReferenceSource;
import com.virginvoyages.crossreference.types.ReferenceType;
import com.virginvoyages.data.entities.ReferenceData;
import lombok.Data;
import lombok.experimental.Accessors;
/**
 * Core entity to store the cross references for the enterprise.
 */
@Data
@Accessors(fluent = true, chain = true)
public class Reference   {
	
  @JsonProperty("auditData")	
  private Audited auditData = null;	

  @JsonProperty("referenceSource")
  private ReferenceSource referenceSource = null;

  @JsonProperty("referenceType")
  private ReferenceType referenceType = null;

  @JsonProperty("referenceID")
  private String referenceID = null;

  @JsonProperty("nativeSourceID")
  private String nativeSourceID = null;

  @JsonProperty("masterID")
  private String masterID = null;

  @JsonProperty("expiry")
  private LocalDate expiry = null;

  @JsonProperty("details")
  private String details = null;
  
  public ReferenceData convertToDataEntity() {
	  return new ReferenceData()
			  .nativeSourceID(this.nativeSourceID())
			  .masterID(this.masterID())
			  .expiry(this.expiry().toString())
			  .details(this.details())
			  .createDate(this.auditData().createDate().toString())
			  .createUser(this.auditData().createUser())
			  .updateDate(this.auditData().updateDate().toString())
			  .updateUser(this.auditData().updateUser());
  }

}

