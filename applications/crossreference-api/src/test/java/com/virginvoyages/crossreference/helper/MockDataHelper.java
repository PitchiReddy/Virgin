package com.virginvoyages.crossreference.helper;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.virginvoyages.api.MockCrossReferenceAPI;
import com.virginvoyages.crossreference.model.Audited;
import com.virginvoyages.crossreference.sources.ReferenceSource;


@Component
public class MockDataHelper {

	@Autowired
	private MockCrossReferenceAPI mockAPI;
	
	public ReferenceSource getReferenceSourceByID() {
		return mockAPI.findReferenceSourceByID("RS1");
	}
	
	public ReferenceSource getDataForCreateReferenceSource() {
		return new ReferenceSource()
				.auditData(createAuditDataForCreate())
				.referenceSourceID("RS1")
				.referenceSourceName("Seaware");
	}
	
	public Audited createAuditDataForCreate() {
		Audited audited = new Audited();
		audited.createDate(LocalDate.now());
		audited.createUser("mockXREFapi");
		audited.updateDate(LocalDate.now());
		audited.updateUser("mockXREFapi");

		return audited;

	}

	public String getValidReferenceSourceByID() {
		return "RS1";
	}
	
	
	public String createReferenceSourceInJson (String referenceSourceID, String referenceSourceName, boolean inActive) {
        return "{ \"referenceSourceID\": \"" + referenceSourceID + "\", " +
                            "\"referenceSourceName\":\"" + referenceSourceName + "\", "+
        					 "\"inActive\":\"" + inActive + "\"}";
	}
}
