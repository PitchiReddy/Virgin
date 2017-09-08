package com.virginvoyages.reference.types.helper;

import org.joda.time.LocalDate;
import org.springframework.stereotype.Service;

import com.virginvoyages.crossreference.model.Audited;
import com.virginvoyages.crossreference.types.ReferenceType;

@Service
public class TestReferenceTypeDataHelper {

	public ReferenceType getReferenceTypeByID() {
		ReferenceType referenceType = new ReferenceType();
		Audited Audited = createAuditDataForCreate();
		referenceType.auditData(Audited);
		referenceType.referenceName("referenceName");
		referenceType.referenceType("referenceType");
		referenceType.referenceTypeID("RT1");
		return referenceType;
	}

	public Audited createAuditDataForCreate(){
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

}
