package com.virginvoyages.data.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.joda.time.LocalDate;
import com.virginvoyages.crossreference.model.Audited;
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

	@Column(name="NAME")
	private String referenceSourceName = null;

	@Column(name="IS_INACTIVE")
	private Boolean inActive = null;
	
	@Column(name="CREATE_DATE")
	private String createDate;

	@Column(name="CREATE_USER")
	private String createUser;

	@Column(name="UPDATE_DATE")
	private String updateDate;

	@Column(name="UPDATE_USER")
	private String updateUser;	
	
	public ReferenceSource convertToBusinessEntity() {
		return new ReferenceSource()
				.referenceSourceName(this.referenceSourceName())
				.referenceSourceID(String.valueOf(this.referenceSourceID()))
				.inActive(this.inActive())
				.auditData(new Audited()
						.createDate(LocalDate.parse(this.createDate()))
						.createUser(this.createUser())
						.updateDate(LocalDate.parse(this.updateDate()))
						.updateUser(this.updateUser())
				);
	}

}
