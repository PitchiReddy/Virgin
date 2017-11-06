package com.virginvoyages.crossreference.sources;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.virginvoyages.data.entities.ReferenceSourceData;
import lombok.Data;
import lombok.experimental.Accessors;
/**
 * Entity that represents the System of Record (SOR)
 */

@Data
@Accessors(fluent = true, chain = true)
public class ReferenceSource   {
  
  @JsonProperty("referenceSourceID")
  private String referenceSourceID = null;

  @JsonProperty("referenceSource")
  private String referenceSource = null;

  @JsonProperty("inActive")
  private Boolean inActive = false;
  
  public ReferenceSourceData convertToDataEntity() {
	  return new ReferenceSourceData()
			  .referenceSource(this.referenceSource())
			  .referenceSourceID(this.referenceSourceID())
			  .inActive(this.inActive());
  }
  
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ReferenceSource)) {
			return false;
		}
		ReferenceSource other = (ReferenceSource) obj;
		return referenceSourceID().equals(other.referenceSourceID());
	}

	@Override
	public int hashCode() {
		return referenceSourceID().hashCode();
	}

}
