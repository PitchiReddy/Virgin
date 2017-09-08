package com.virginvoyages.crossreference.sources.helper;

import org.joda.time.LocalDate;
import org.springframework.stereotype.Service;
import com.virginvoyages.crossreference.model.Audited;
import com.virginvoyages.crossreference.sources.ReferenceSource;

@Service
public class TestReferenceSourceDataHelper {

	public ReferenceSource createReferenceSource(){
		ReferenceSource referenceSource = new ReferenceSource();
		Audited Audited = createAuditDataForCreate();
		referenceSource.auditData(Audited);
		referenceSource.referenceSourceID("RS1");
		referenceSource.referenceSourceName("Seaware");
		referenceSource.inActive(true);
		
		return referenceSource;
		
	}
	
	public Audited createAuditDataForCreate(){
		Audited audited = new Audited();
		audited.createDate(LocalDate.now());
		audited.createUser("mockXREFapi");
		audited.updateDate(LocalDate.now());
		audited.updateUser("mockXREFapi");
		
		return audited;
		
	}
	
	public String getInvalidReferenceSourceByID(){
		return "invalidRef";
	}
}
