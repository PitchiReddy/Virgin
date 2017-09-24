package com.virginvoyages.data.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.joda.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

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
	private String referenceSourceID = null;

	@Column(name="NAME")
	private String referenceSourceName = null;

	@JsonProperty("IS_INACTIVE")
	private Boolean inActive = null;
	
	@Column(name="CREATE_DATE")
	private LocalDate createDate;

	@Column(name="CREATE_USER")
	private String createUser;

	@Column(name="UPDATE_DATE")
	private LocalDate updateDate;

	@Column(name="UPDATE_USER")
	private String updateUser;	

}
