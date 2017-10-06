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
	private Long referenceID;

	@Column(name="NATIVE_SOURCE_ID")
	private String nativeSourceID;
	
	@Column(name="MASTER_ID")
	private String masterID;

	@OneToOne(cascade = {CascadeType.MERGE},fetch = FetchType.EAGER)
    @JoinColumn(name = "REFERENCE_TYPE_ID")
	private ReferenceTypeData referenceTypeData;

	public Reference convertToBusinessEntity() {
		return new Reference()
				.referenceID(String.valueOf(this.referenceID()))
				.nativeSourceID(String.valueOf(this.nativeSourceID()))
				.masterID(String.valueOf(this.masterID()))
				.referenceTypeID(String.valueOf(this.referenceTypeData()));
				
				
}
	
}	
