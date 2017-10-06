package com.virginvoyages;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.virginvoyages.crossreference.helper.TestDataHelper;
import com.virginvoyages.crossreference.model.Audited;
import com.virginvoyages.crossreference.references.Reference;
import com.virginvoyages.crossreference.sources.ReferenceSource;
import com.virginvoyages.crossreference.types.ReferenceType;

public class CrossReferenceFunctionalTestSupport extends FunctionalTestSupport {
	
	@Autowired
	private TestDataHelper testDataHelper;
	
	@Test
    public void contextLoads() {
    }

	public ReferenceSource createTestReferenceSource() {

		ReferenceSource referenceSource = testDataHelper.getDataForCreateReferenceSource();

		Map<String, Object> parameters = new HashMap<String, Object>();
		Audited audited = testDataHelper.createAuditDataForCreate();
		referenceSource.auditData(audited);
		parameters.put("auditData", referenceSource.auditData(audited));
		parameters.put("referenceSourceID", referenceSource.referenceSourceID());
		parameters.put("referenceSourceName", referenceSource.referenceSourceName());
		parameters.put("inActive", referenceSource.inActive());

		given()
			.contentType("application/json")
			.body(parameters)
			.post("/xref-api/v1/sources").

		then()
			.statusCode(200);

		return referenceSource;
	}
	
	public void deleteTestReferenceSource(String referenceSourceID) {

		given()
			.contentType("application/json")
			.delete("/xref-api/v1/sources/" + referenceSourceID).

		then()
			.statusCode(200);
	}

	public ReferenceType createTestReferenceType() {

		ReferenceType referenceType = testDataHelper.getDataForCreateReferenceType();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("referenceTypeID", referenceType.referenceTypeID());
		parameters.put("referenceName", referenceType.referenceName());
		parameters.put("referenceType", referenceType.referenceType());

		// create reference type
		given()
			.contentType("application/json")
			.body(parameters)
			.post("/xref-api/v1/types/").

		then()
			.assertThat()
			.statusCode(200)
			.log()
			.all();

		return referenceType;
	}
	
	public void deleteTestReferenceType(String referenceTypeID) {

		given()
			.contentType("application/json")
			.delete("/xref-api/v1/types/" + referenceTypeID).

		then()
			.statusCode(200);
	}
	
	public Reference createTestReferences() {

		Reference reference = testDataHelper.getDataForCreateReference();
		Map<String, Object> parameters = new HashMap<String, Object>();
		Audited audited = testDataHelper.createAuditDataForCreate();
		parameters.put("auditData", reference.auditData(audited));
		parameters.put("referenceType", reference.referenceType());
		parameters.put("referenceSource",reference.referenceSource());
		parameters.put("referenceID", reference.referenceID());
		parameters.put("details", reference.details());
		parameters.put("expiry", LocalDate.now());
		parameters.put("masterID", reference.masterID());
		parameters.put("nativeSourceID", reference.nativeSourceID());
		
		// create references 
		given()
		     .contentType("application/json") 
		     .body(parameters)
		     .post("/xref-api/v1/references/").

		then()
		 	.assertThat()
		 	.statusCode(200)
		 	.log()
		 	.all();
		
		return reference;
	}
	
	public void deleteTestReference(String referenceID) {
		
		given()
			.contentType("application/json")
			.delete("/xref-api/v1/references/" + referenceID).

		then()
			.statusCode(200);
	}

}
