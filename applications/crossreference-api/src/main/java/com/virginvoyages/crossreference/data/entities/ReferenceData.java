package com.virginvoyages.crossreference.data.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Table(name = "REFERENCE")
@Data
@Accessors(fluent = true, chain = true)
public class ReferenceData {

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid",strategy = "uuid")
	@Column(name="REFERENCE_ID")
	private String referenceID;

	@Column(name="NATIVE_SOURCE_ID_VALUE")
	private String nativeSourceIDValue;
	
	@Column(name="MASTER_ID")
	private String masterID;

	@OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "REFERENCE_TYPE_ID")
	private ReferenceTypeData referenceTypeData;
	
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
