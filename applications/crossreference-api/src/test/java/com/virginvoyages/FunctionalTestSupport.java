package com.virginvoyages;

import static io.restassured.RestAssured.given;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.crossreference.references.Reference;
import com.virginvoyages.crossreference.types.ReferenceType;

import static io.restassured.RestAssured.port;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class FunctionalTestSupport {
	protected RequestSpecification specification;
	@LocalServerPort
	int localPort;

	@Before
	public void setUp() {
		port = localPort;
		specification = RestAssured.given().header("correlationID", UUID.randomUUID().toString());
		// specification.log().everything();
	}

	public String createTestReferenceTypeAndGetTypeID(ReferenceType referenceType) {
		Map<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("referenceType", referenceType.referenceType());
		parameters.put("referenceTypeID", referenceType.referenceTypeID());
		parameters.put("referenceName", referenceType.referenceName());

		String referenceTypeID = 
			given().
					contentType("application/json").
					body(parameters).
					post("/v1/types").

			then().
					statusCode(200).
					extract().
					path("referenceTypeID");

		return referenceTypeID;
	}

	public void deleteTestReferenceType(String referenceTypeID) {
		// Cleanup
		given().contentType("application/json").delete("v1/types" + referenceTypeID).

				then().statusCode(200);
	}

	public String createTestReferencesAndGetReferences(Reference reference) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("referenceID", reference.referenceID());
		parameters.put("masterID", reference.masterID());
		parameters.put("auditData", reference.auditData());
		String referenceID = 
			given().
					contentType("application/json").
					body(parameters).
					post("/v1/references").

			then().
					statusCode(200).
					extract().
					path("referenceID");

		return referenceID;

	}

	public void deleteTestReferences(String referenceID) {
		// Cleanup
		given().contentType("application/json").delete("/v1/references" + referenceID).

				then().statusCode(200);
	}

}
