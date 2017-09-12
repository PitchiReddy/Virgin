package com.virginvoyages;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.port;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.crossreference.helper.TestDataHelper;
import com.virginvoyages.crossreference.model.Audited;
import com.virginvoyages.crossreference.sources.ReferenceSource;
import com.virginvoyages.crossreference.types.ReferenceType;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class FunctionalTestSupport {

	@Autowired
	private TestDataHelper testDataHelper;

	protected RequestSpecification specification;
	@LocalServerPort
	int localPort;

	@Before
	public void setUp() {
		port = localPort;
		specification = RestAssured.given().header("correlationID", UUID.randomUUID().toString());
		// specification.log().everything();
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
