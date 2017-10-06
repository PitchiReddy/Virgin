package com.virginvoyages.data.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import com.virginvoyages.crossreference.types.ReferenceType;
import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Table(name = "REFERENCE_TYPE")
@Data
@Accessors(fluent = true, chain = true)
public class ReferenceTypeData {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="REFERENCE_TYPE_ID")
	private Long referenceTypeID;

	@Column(name="REFERENCE_TYPE")
	private String referenceType = null;
	
	@OneToOne(cascade = {CascadeType.MERGE},fetch = FetchType.EAGER)
    @JoinColumn(name = "REFERENCE_SOURCE_ID")
	private ReferenceSourceData referenceSourceData;
		
	public ReferenceType convertToBusinessEntity() {
		return new ReferenceType()
				.referenceType(this.referenceType())
				.referenceSourceID(String.valueOf(this.referenceSourceData().referenceSourceID()))
				.referenceTypeID(String.valueOf(this.referenceTypeID().longValue()));
				
	}

}
