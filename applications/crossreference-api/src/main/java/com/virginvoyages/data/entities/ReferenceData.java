package com.virginvoyages.data.entities;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.joda.time.LocalDate;

import lombok.Data;
import lombok.experimental.Accessors;

//@Entity
//@Table(name = "REFERENCE")
@Data
@Accessors(fluent = true, chain = true)
public class ReferenceData {

	/*@JsonProperty("referenceSource")
	private ReferenceSource referenceSource = null;

	@JsonProperty("referenceType")
	private ReferenceType referenceType = null;*/

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="REFERENCE_ID")
	private String referenceID;

	@Column(name="NATIVE_SOURCE_ID")
	private String nativeSourceID;
	
	@Column(name="MASTER_ID")
	private String masterID;

	@Column(name="EXPIRY_DATE")
	private LocalDate expiry = null;

	@Column(name="DETAILS")
	private String details = null;
	
	@Column(name="CREATE_DATE")
	private LocalDate createDate;

	@Column(name="CREATE_USER")
	private String createUser;

	@Column(name="UPDATE_DATE")
	private LocalDate updateDate;

	@Column(name="UPDATE_USER")
	private String updateUser;	

}
