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
import com.virginvoyages.crossreference.references.Reference;
import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Table(name = "REFERENCE")
@Data
@Accessors(fluent = true, chain = true)
public class ReferenceData {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="REFERENCE_ID")
	private String referenceID;

	@Column(name="NATIVE_SOURCE_ID_VALUE")
	private String nativeSourceIDValue;
	
	@Column(name="MASTER_ID")
	private String masterID;

	@OneToOne(cascade = {CascadeType.MERGE},fetch = FetchType.EAGER)
    @JoinColumn(name = "REFERENCE_TYPE_ID")
	private ReferenceTypeData referenceTypeData;

	public Reference convertToBusinessEntity() {
		return new Reference()
				.referenceID(String.valueOf(this.referenceID()))
				.nativeSourceIDValue(String.valueOf(this.nativeSourceIDValue()))
				.masterID(String.valueOf(this.masterID()))
				.referenceTypeID(String.valueOf(this.referenceTypeData()));
				
				
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ReferenceData)) {
			return false;
		}
		ReferenceData other = (ReferenceData) obj;
		return referenceID().equals(other.referenceID());
	}
	
	@Override
	public int hashCode() {
		return referenceID().hashCode();
	}
	
}	
