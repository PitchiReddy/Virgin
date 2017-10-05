package com.virginvoyages.data.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.virginvoyages.crossreference.sources.ReferenceSource;
import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Table(name = "REFERENCE_SOURCE")
@Data
@Accessors(fluent = true, chain = true)
public class ReferenceSourceData {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="REFERENCE_SOURCE_ID")
	private Long referenceSourceID = null;

	@Column(name="REFERENCE_SOURCE", nullable=false)
	private String referenceSourceName = null;

	@Column(name="IS_INACTIVE")
	private Boolean inActive = null;

	
	public ReferenceSource convertToBusinessEntity() {
		return new ReferenceSource()
				.referenceSourceName(this.referenceSourceName())
				.inActive(this.inActive());
			
	}

}
