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
import com.virginvoyages.data.util.GUIDGenerator;

import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Table(name = "REFERENCE_TYPE")
@Data
@Accessors(fluent = true, chain = true)
public class ReferenceTypeData {

	@Id
    @Column(name="REFERENCE_TYPE_ID")
	private String referenceTypeID;

	@Column(name="REFERENCE_TYPE")
	private String referenceType = null;
	
	@OneToOne(cascade = {CascadeType.MERGE},fetch = FetchType.EAGER)
    @JoinColumn(name = "REFERENCE_SOURCE_ID")
	private ReferenceSourceData referenceSourceData;
	
	public ReferenceTypeData() {
		referenceTypeID(GUIDGenerator.generateRandomGUID());
	}
		
	public ReferenceType convertToBusinessEntity() {
		return new ReferenceType()
				.referenceType(this.referenceType())
				.referenceSourceID(this.referenceSourceData().referenceSourceID())
				.referenceTypeID(this.referenceTypeID());
				
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ReferenceSourceData)) {
			return false;
		}
		ReferenceTypeData other = (ReferenceTypeData) obj;
		return referenceTypeID().equals(other.referenceTypeID());
	}
	
	@Override
	public int hashCode() {
		return referenceTypeID().hashCode();
	}

}
