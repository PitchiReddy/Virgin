package com.virginvoyages.crossreference.helper;

import org.joda.time.LocalDate;
import org.springframework.stereotype.Service;

import com.virginvoyages.crossreference.model.Audited;
import com.virginvoyages.crossreference.references.Reference;
import com.virginvoyages.crossreference.sources.ReferenceSource;
import com.virginvoyages.crossreference.types.ReferenceType;

@Service
public class TestDataHelper {

	public ReferenceSource getDataForCreateReferenceSource() {
		
		return new ReferenceSource().auditData(createAuditDataForCreate()).referenceSourceID("RS30")
				.referenceSourceName("Seaware");
	}

	public ReferenceType getDataForCreateReferenceType() {

		return new ReferenceType().auditData(createAuditDataForCreate()).referenceTypeID("RT30")
				.referenceType("Reservation").referenceName("Activity");

	}

	public Reference getDataForCreateReference() {

		return new Reference()
				.auditData(createAuditDataForCreate())
				.referenceType(getDataForCreateReferenceType())
				.referenceSource(getDataForCreateReferenceSource())
				.referenceID("R30")
				.details("Dummy Reference Entry 30")
				.expiry(LocalDate.now())
				.masterID("M30")
				.nativeSourceID("NSID30");
	}

	public ReferenceType getReferenceTypeByID() {
		ReferenceType referenceType = new ReferenceType();
		Audited Audited = createAuditDataForCreate();
		referenceType.auditData(Audited);
		referenceType.referenceName("referenceName");
		referenceType.referenceType("referenceType");
		referenceType.referenceTypeID("RT1");
		return referenceType;
	}

	public Audited createAuditDataForCreate() {
		Audited audited = new Audited();
		audited.createDate(LocalDate.now());
		audited.createUser("siva1");
		audited.updateDate(LocalDate.now());
		audited.updateUser("siva2");

		return audited;

	}

	public String deleteReferenceTypeByID() {

		return "RT1";
	}

	public Reference getReferenceByID() {
		Reference reference = new Reference();
		Audited audited = createAuditDataForCreate();
		reference.auditData(audited);
		ReferenceSource referenceSource = creatreferenceSourceDataForCreate();
		reference.referenceSource(referenceSource);
		ReferenceType referenceType = creatreferenceTypeDataForCreate();
		reference.referenceType(referenceType);
		reference.referenceID("RS1");
		reference.masterID("M1");
		reference.expiry();
		reference.nativeSourceID("NSID1");
		reference.details("details");
		return reference;
	}

	private ReferenceType creatreferenceTypeDataForCreate() {
		ReferenceType referenceType = new ReferenceType();
		Audited Audited = createAuditDataForCreate();
		referenceType.auditData(Audited);
		referenceType.referenceTypeID("referenceSourceID");
		referenceType.referenceName("referenceSourceName");
		referenceType.referenceType("referenceSource");
		return referenceType;
	}

	public ReferenceSource creatreferenceSourceDataForCreate() {
		ReferenceSource referenceSource = new ReferenceSource();
		Audited Audited = createAuditDataForCreate();
		referenceSource.auditData(Audited);
		referenceSource.referenceSourceID("referenceSourceID");
		referenceSource.referenceSourceName("referenceSourceName");
		return referenceSource;

	}

	public String deleteReferenceByID() {

		return "RT1";
	}

}
