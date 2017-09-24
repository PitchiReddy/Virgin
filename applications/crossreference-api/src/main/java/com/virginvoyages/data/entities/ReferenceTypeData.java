package com.virginvoyages.data.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.joda.time.LocalDate;

import com.virginvoyages.crossreference.model.Audited;
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

	@Column(name="NAME")
	private String referenceName = null;

	@Column(name="TYPE")
	private String referenceType = null;
	
	@Column(name="CREATE_DATE")
	private String createDate;

	@Column(name="CREATE_USER")
	private String createUser;

	@Column(name="UPDATE_DATE")
	private String updateDate;

	@Column(name="UPDATE_USER")
	private String updateUser;	
	
	public ReferenceType convertToBusinessEntity() {
		return new ReferenceType()
				.referenceName(this.referenceName())
				.referenceType(this.referenceType())
				.referenceTypeID(String.valueOf(this.referenceTypeID()))
				.auditData(new Audited()
						.createDate(LocalDate.parse(this.createDate()))
						.createUser(this.createUser())
						.updateDate(LocalDate.parse(this.updateDate()))
						.updateUser(this.updateUser())
				);
	}

}
