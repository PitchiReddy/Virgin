package com.virginvoyages.crossreference.sources;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.virginvoyages.crossreference.model.Audited;
import com.virginvoyages.data.entities.ReferenceSourceData;
import lombok.Data;
import lombok.experimental.Accessors;
/**
 * Entity that represents the System of Record (SOR)
 */

@Data
@Accessors(fluent = true, chain = true)
public class ReferenceSource   {
  
  @JsonProperty("auditData")	
  private Audited auditData = null;	

  @JsonProperty("referenceSourceID")
  private String referenceSourceID = null;

  @JsonProperty("referenceSourceName")
  private String referenceSourceName = null;

  @JsonProperty("inActive")
  private Boolean inActive = null;
  
  public ReferenceSourceData convertToDataEntity() {
	  return new ReferenceSourceData()
			  .referenceSourceName(this.referenceSourceName())
			  .inActive(this.inActive())
			  .createDate(this.auditData().createDate().toString())
			  .createUser(this.auditData().createUser())
			  .updateDate(this.auditData().updateDate().toString())
			  .updateUser(this.auditData().updateUser());
  }

 }

