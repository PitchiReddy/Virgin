package com.virginvoyages.crossreference.helper;

import org.joda.time.LocalDate;
import org.springframework.stereotype.Service;

import com.virginvoyages.crossreference.model.Audited;
import com.virginvoyages.crossreference.references.Reference;
import com.virginvoyages.crossreference.sources.ReferenceSource;
import com.virginvoyages.crossreference.types.ReferenceType;

/**
 * Helper class for testcases
 * @author rpraveen
 *
 */
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

		return new Reference().auditData(createAuditDataForCreate()).referenceType(getDataForCreateReferenceType())
				.referenceSource(getDataForCreateReferenceSource()).referenceID("R30")
				.details("Dummy Reference Entry 30").expiry(LocalDate.now()).masterID("M30").nativeSourceID("NSID30");
	}

	public Audited createAuditDataForCreate() {
		Audited audited = new Audited();
		audited.createDate(LocalDate.now());
		audited.createUser("siva1");
		audited.updateDate(LocalDate.now());
		audited.updateUser("siva2");

		return audited;

	}
}
