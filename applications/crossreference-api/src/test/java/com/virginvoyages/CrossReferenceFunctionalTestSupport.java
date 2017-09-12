package com.virginvoyages;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.virginvoyages.crossreference.helper.TestDataHelper;
import com.virginvoyages.crossreference.model.Audited;
import com.virginvoyages.crossreference.sources.ReferenceSource;
import com.virginvoyages.crossreference.types.ReferenceType;

public class CrossReferenceFunctionalTestSupport extends FunctionalTestSupport {
	
	@Autowired
	private TestDataHelper testDataHelper;
	
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
			.post("/v1/sources").

		then()
			.statusCode(200);

		return referenceSource;
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
			.post("/v1/types/").

		then()
			.assertThat()
			.statusCode(200)
			.log()
			.all();

		return referenceType;
	}

	public void deleteTestReferenceSource(String referenceSourceID) {

		given()
			.contentType("application/json")
			.delete("/v1/sources/" + referenceSourceID).

		then()
			.statusCode(200);
	}

	public void deleteTestReferenceType(String referenceTypeID) {

		given()
			.contentType("application/json")
			.delete("/v1/types/" + referenceTypeID).

		then()
			.statusCode(200);
	}

}
