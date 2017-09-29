package com.virginvoyages.data.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.joda.time.LocalDate;
import com.virginvoyages.crossreference.model.Audited;
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

	@Column(name="EXPIRY_DATE")
	private String expiry = null;

	@Column(name="DETAILS")
	private String details = null;
	
	@Column(name="CREATE_DATE")
	private String createDate;

	@Column(name="CREATE_USER")
	private String createUser;

	@Column(name="UPDATE_DATE")
	private String updateDate;

	@Column(name="UPDATE_USER")
	private String updateUser;	
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "REFERENCE_SOURCE_ID")
	private ReferenceSourceData referenceSourceID;

	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "REFERENCE_TYPE_ID")
	private ReferenceTypeData referenceTypeID;

	public Reference convertToBusinessEntity() {
		return new Reference()
				.referenceID(String.valueOf(this.referenceID()))
				.nativeSourceID(String.valueOf(this.nativeSourceID()))
				.masterID(String.valueOf(this.masterID()))
				.expiry(LocalDate.parse(this.expiry()))
				.details(this.details())
				.auditData(new Audited()
						.createDate(LocalDate.parse(this.createDate()))
						.createUser(this.createUser())
						.updateDate(LocalDate.parse(this.updateDate()))
						.updateUser(this.updateUser())
				);
				
}
	
}	
