package com.virginvoyages.crossreference.types;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.FunctionalTestSupport;
import com.virginvoyages.crossreference.types.ReferenceType;
import com.virginvoyages.reference.types.helper.TestReferenceTypeDataHelper;

import static org.hamcrest.CoreMatchers.equalTo;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import static io.restassured.RestAssured.given;
import org.junit.runner.RunWith;

@RunWith(SpringRunner.class)
public class ReferenceTypeControllerFuncTest extends FunctionalTestSupport {

	@Autowired
	private TestReferenceTypeDataHelper testReferenceTypeDataHelper;

	@Test
	public void givenValidReferenceTypeIdFindReferenceTypeByIdShouldFindReferenceType() {
		ReferenceType referenceType = testReferenceTypeDataHelper.getReferenceTypeByID();
		String referenceTypeID = createTestReferenceTypeAndGetTypeID(referenceType);

		given()
				.contentType("application/json")
				.get("/v1/types/" + referenceType.referenceTypeID())
		.then()
				.assertThat().statusCode(200)
				.assertThat().body("referenceTypeID", equalTo(referenceType.referenceTypeID()))
				.log()
				.all();
		 //cleanup
		deleteTestReferenceType(referenceTypeID);

	}

	@Test
	public void givenValidReferenceTypeIdDeleteReferenceTypeByIdShouldDeleteReferenceType() {
		ReferenceType referenceType = testReferenceTypeDataHelper.getReferenceTypeByID();
		String referenceTypeID = createTestReferenceTypeAndGetTypeID(referenceType);
	//	String referenceTypeID = testReferenceTypeDataHelper.deleteReferenceTypeByID();
		given().
				contentType("application/json").
				delete("/v1/types/" + referenceTypeID).
		then().
				assertThat().statusCode(200).
				log().all().extract();
		
		 //cleanup
		deleteTestReferenceType(referenceTypeID);

	}
	
	@Test
	public void givenValidReferenceTypeIdShouldAddReferenceType() {
		ReferenceType referenceType = testReferenceTypeDataHelper.getReferenceTypeByID();
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		parameters.put("referenceType", referenceType.referenceType());
		parameters.put("referenceName", referenceType.referenceName());
		parameters.put("referenceTypeID", referenceType.referenceTypeID());
		given()
				.contentType("application/json")
				.body(parameters)
				.post("/v1/types/")
		.then()
				.assertThat().statusCode(200)
				.log()
				.all();
	}
	
	@Test
	public void givenValidReferenceTypeIdUpdateReferenceTypeByIdShouldUpdateReferenceType() {
		ReferenceType referenceType = testReferenceTypeDataHelper.getReferenceTypeByID(); 
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		parameters.put("referenceType", referenceType.referenceType());
		parameters.put("referenceName", referenceType.referenceName());
		parameters.put("referenceTypeID", referenceType.referenceTypeID());
		given()
				.contentType("application/json")
				.body(parameters)
				.put("/v1/types/" + referenceType.referenceTypeID())
		.then()
				.assertThat().statusCode(200)
				.log()
				.all();
	}
}
