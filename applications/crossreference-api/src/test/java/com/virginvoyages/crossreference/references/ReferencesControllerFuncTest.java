package com.virginvoyages.crossreference.references;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.CrossReferenceFunctionalTestSupport;
import com.virginvoyages.crossreference.data.entities.ReferenceData;
import com.virginvoyages.crossreference.helper.TestDataHelper;

import io.restassured.path.json.JsonPath;

@RunWith(SpringRunner.class)
public class ReferencesControllerFuncTest extends CrossReferenceFunctionalTestSupport {

	@Autowired
	private TestDataHelper testDataHelper;

	@Test
	public void givenValidReferenceAndAddReferenceShouldCreateReference() {

		ReferenceData reference= testDataHelper.getReferenceDataEntity();
		JsonPath referenceTypeJson = createTestReferenceType();

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("referenceTypeID", referenceTypeJson.getString("referenceTypeID"));
		parameters.put("masterID", reference.masterID());
		parameters.put("nativeSourceIDValue", reference.nativeSourceIDValue());
		//parameters.put("referenceID", reference.referenceID());

		JsonPath createdReferenceJson=given()
				.contentType("application/json")
				.body(parameters)
				.post("/xref-api/v1/references/")

		.then()
				.assertThat()
				.statusCode(200)
				.log()
				.all()
				.extract()
				.response()
				.jsonPath();

		given().
				contentType("application/json").
				get("/xref-api/v1/references/" + createdReferenceJson.getString("referenceID")).
		then().
				assertThat().
				statusCode(200).
				body("referenceID", equalTo(createdReferenceJson.getString("referenceID"))).
				body("masterID", equalTo(createdReferenceJson.getString("masterID"))).
				body("nativeSourceIDValue", equalTo(createdReferenceJson.getString("nativeSourceIDValue"))).
				log().
				all();

		deleteTestReference(createdReferenceJson.getString("referenceID"));
		deleteTestReferenceType(referenceTypeJson.getString("referenceTypeID"));
		deleteTestReferenceSource(referenceTypeJson.getString("referenceSourceID"));

	}

	@Test
	public void givenInvalidReferenceIDInRequestDeleteReferenceByIdShouldThrowDataNotFoundException() {

		//Test invalid ReferenceTypeId Delete
		given().
				contentType("application/json").
				delete("/xref-api/v1/references/" + testDataHelper.getRandomAlphanumericString()).
		then().
				assertThat().
				statusCode(404).
				body("exception", equalTo("com.virginvoyages.exception.DataNotFoundException")).
				log().
				all();
	}

	@Test
	public void givenValidReferenceDeleteReferenceByIdShouldDeleteReference() {

		//Create test reference

		JsonPath testReferenceSource = createTestReferenceSource();
		JsonPath testReferenceType = createTestReferenceType(testReferenceSource);

		JsonPath createdReferenceJson = createTestReference(testReferenceType);

		//Test successfuly created
		given().
			contentType("application/json").
			get("/xref-api/v1/references/" + createdReferenceJson.getString("referenceID")).
		then().
			assertThat().statusCode(200).
			assertThat().body("referenceID", equalTo(createdReferenceJson.getString("referenceID")));

	    //Delete
		given().
				contentType("application/json").
				delete("/xref-api/v1/references/" + createdReferenceJson.getString("referenceID")).
		then().
				assertThat().statusCode(200).
				log().
				all();
		//Try to find deleted reference
		given().
				contentType("application/json").
				get("/xref-api/v1/references/" + createdReferenceJson.getString("referenceID")).
		then().
				assertThat().statusCode(404);

		//cleanup
		deleteTestReferenceType(testReferenceType.getString("referenceTypeID"));
		deleteTestReferenceSource(testReferenceSource.getString("referenceSourceID"));

	}

	@Test
	public void givenEmptyReferenceIDInRequestBodyDeleteReferenceByIdShouldThrowSomeException() {

		//Test invalid ReferenceTypeId Delete
		given().
				contentType("application/json").
		when().
				delete("/xref-api/v1/references/"+" ").
		then().
				assertThat().statusCode(405).
				assertThat().body("exception", equalTo("org.springframework.web.HttpRequestMethodNotSupportedException")).
				log().
				all();

	}

	/*@Test
	public void givenValidReferenceSourceIDInRequestBodyUpdateReferenceTypeShouldUpdateReferenceTypeWithNewSourceID() {


		JsonPath referenceJson = createTestReference();
		JsonPath referenceTypeToUpdateJson = createTestReferenceType();
		String referenceSourceID = (String) parameters.get("referenceSourceID");

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("referenceTypeID", referenceJson.getString("referenceTypeID"));
		parameters.put("masterID", referenceJson.getString("masterID"));
		parameters.put("nativeSourceIDValue", referenceJson.getString("nativeSourceIDValue"));
		parameters.put("referenceID", referenceJson.getString("referenceID"));

		given()
				.contentType("application/json")
				.body(parameters)
				.put("/xref-api/v1/references")

		.then().
		assertThat().statusCode(200).
		assertThat().body("referenceID", equalTo(referenceJson.getString("referenceID"))).
		assertThat().body("masterID", equalTo(referenceJson.getString("masterID"))).
		assertThat().body("nativeSourceIDValue", equalTo(referenceJson.getString("nativeSourceIDValue"))).
		log().
		all();


		//cleanup
		deleteTestReference(referenceJson.getString("referenceID"));
		deleteTestReferenceType(referenceJson.getString("referenceTypeID"));
		deleteTestReferenceType(referenceTypeToUpdateJson.getString("referenceTypeID"));
		deleteTestReferenceSource(referenceTypeToUpdateJson.getString("referenceSourceID"));
		deleteTestReferenceSource(referenceSourceID);
	} */

	/*@Test
	public void givenValidReferenceIDGetReferenceByIdShouldReturnReference() {

		//Create test reference
		JsonPath referenceJson = createTestReference();

		String referenceSourceID = (String)referenceParam.get("referenceSourceID");

		//Test
		given().
				contentType("application/json").
				get("/xref-api/v1/references/" + referenceJson.getString("referenceID")).
		then().
				assertThat().statusCode(200).
				assertThat().body("referenceID", equalTo(referenceJson.getString("referenceID"))).
				assertThat().body("masterID", equalTo(referenceJson.getString("masterID"))).
				assertThat().body("nativeSourceIDValue", equalTo(referenceJson.getString("nativeSourceIDValue"))).
				assertThat().body("referenceTypeID", equalTo(referenceJson.getString("referenceTypeID"))).
				log().
				all();

		//cleanup
		deleteTestReference(referenceJson.getString("referenceID"));
		deleteTestReferenceType(referenceJson.getString("referenceTypeID"));
		deleteTestReferenceSource(referenceSourceID);
	}*/

	@Test
	public void givenInValidReferenceIDGetReferenceByIdShouldThrowDataNotFoundExceptionException() {

		//Test
		given().
				contentType("application/json").
				get("/xref-api/v1/references/" + testDataHelper.getRandomAlphanumericString()).
		then().
				assertThat().statusCode(404).
				assertThat().body("exception", equalTo("com.virginvoyages.exception.DataNotFoundException")).
				log().
				all();

	}

	/*@Test
	public void givenValidReferenceExistFindReferencesShouldReturnListOfReferences() {

		//create reference
		JsonPath referenceJson = createTestReference();

		String referenceSourceID = (String)referenceParam.get("referenceSourceID");

	    ValidatableResponse response =
	    given()
				.contentType("application/json")
				.param("page", 1)
				.param("size", 10)
				.get("/xref-api/v1/references/")

	    .then()
				.assertThat().statusCode(200)
				.log()
				.all();

	    assertThat(response.extract().jsonPath().getMap("$").size(), greaterThan(0));

		//cleanup
		deleteTestReference(referenceJson.getString("referenceID"));
		deleteTestReferenceType(referenceJson.getString("referenceTypeID"));
		deleteTestReferenceSource(referenceSourceID);
	}*/

	@Test
	public void givenEmptyReferenceIDInFindReferenceByIDShouldThrowBadRequestException() {
		given().
				contentType("application/json").
				get("/xref-api/v1/references/" +" ").
		then().
				assertThat().statusCode(400).
				body("exception",equalTo("org.springframework.web.bind.MissingServletRequestParameterException")).
				log().
				all();
	}

	@Test
	public void givenMaxReferenceIDInFindReferenceByIDShouldThrowMaximumRequestIDException() {
		given().
				contentType("application/json").
				get("/xref-api/v1/references/" + testDataHelper.getInvalidReferenceID()).
		then().
				assertThat().statusCode(404).
				body("exception",equalTo("com.virginvoyages.crossreference.exception.ReferenceIDMaxRequestSizeException")).
				log().
				all();
	}

	@Test
	public void givenEmptyReferenceIdUpdateReferenceShouldThrowMandatoryFieldsMissingException() {

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("referenceID", "");
		parameters.put("nativeSourceIDValue", "update");
		given()
				.contentType("application/json")
				.body(parameters)
				.put("/xref-api/v1/references")

		.then()
				.assertThat().statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED)
				.body("exception",equalTo("com.virginvoyages.exception.MandatoryFieldsMissingException"))
				.log()
				.all();

	}


	@Test
	public void givenValidReferenceFindReferencesMasterShouldReturnOneorMoreReferences() {
		
		JsonPath referenceTypeJson = createTestReferenceType();

		JsonPath createdReferenceJson = createTestReference(referenceTypeJson);

		given().contentType("application/json")
				.get("/xref-api/v1/references/search/findByMaster?masterID= "
						+ createdReferenceJson.getString("masterID") + "&targetTypeID="
						+ createdReferenceJson.getString("targetReferenceTypeID"))
				.then().assertThat().statusCode(200).log().all();

		//cleanup
		deleteTestReference(createdReferenceJson.getString("referenceID"));
		deleteTestReferenceType(createdReferenceJson.getString("referenceTypeID"));
		deleteTestReferenceSource(referenceTypeJson.getString("referenceSourceID"));

	}
	
	@Test
	public void givenValidReferenceFindReferencesTypeShouldReturnOneorMoreReferences() {
		JsonPath referenceTypeJson = createTestReferenceType();

		JsonPath createdReferenceJson = createTestReference(referenceTypeJson);

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("referenceTypeID", referenceTypeJson.getString("referenceTypeID"));
		parameters.put("masterID", createdReferenceJson.getString("masterID"));
		parameters.put("nativeSourceIDValue", createdReferenceJson.getString("nativeSourceIDValue"));
		parameters.put("targetReferenceTypeID", createdReferenceJson.getString("referenceTypeID"));

		given()
				.contentType("application/json")
				.body(parameters)
				.post("/xref-api/v1/references/search/findByType")

		.then()
				.assertThat()
				.statusCode(200)
				.log()
				.all();
			
		deleteTestReference(createdReferenceJson.getString("referenceID"));
		deleteTestReferenceType(referenceTypeJson.getString("referenceTypeID"));
		deleteTestReferenceSource(referenceTypeJson.getString("referenceSourceID"));

	}
	
	@Test
	public void givenValidReferenceFindReferencesTypeAndTargetTypeShouldReturnOneorMoreReferences() {
		JsonPath referenceTypeJson = createTestReferenceType();

		JsonPath createdReferenceJson = createTestReference(referenceTypeJson);

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("referenceTypeID", referenceTypeJson.getString("referenceTypeID"));
		parameters.put("masterID", createdReferenceJson.getString("masterID"));
		parameters.put("nativeSourceIDValue", createdReferenceJson.getString("nativeSourceIDValue"));
		parameters.put("targetReferenceTypeID", createdReferenceJson.getString("referenceTypeID"));

		given()
				.contentType("application/json")
				.body(parameters)
				.post("/xref-api/v1/references/search/findByTypeAndTargetType")

		.then()
				.assertThat()
				.statusCode(200)
				.log()
				.all();
			
		deleteTestReference(createdReferenceJson.getString("referenceID"));
		deleteTestReferenceType(referenceTypeJson.getString("referenceTypeID"));
		deleteTestReferenceSource(referenceTypeJson.getString("referenceSourceID"));

	}
	// Find References
	@Test
	public void givenValidReferencesExistFindReferencesShouldReturnListOfReferencesAsPerSizeParameter() {

		JsonPath testReferenceSource = createTestReferenceSource();
		JsonPath testReferenceType = createTestReferenceType(testReferenceSource);

		JsonPath createdReferenceJson1 = createTestReference(testReferenceType);
		JsonPath createdReferenceJson2 = createTestReference(testReferenceType);
		JsonPath createdReferenceJson3 = createTestReference(testReferenceType);

		given()
			.contentType("application/json")
			.param("page", 0)
			.param("size", 2)
			.get("/xref-api/v1/references/")
       .then()
			.assertThat()
			.statusCode(200)
			.body("_embedded.references", hasSize(2))
			.body("page.size", equalTo(2))
			.log()
			.all();

		//cleanup
		deleteTestReference(createdReferenceJson1.getString("referenceID"));
		deleteTestReference(createdReferenceJson2.getString("referenceID"));
		deleteTestReference(createdReferenceJson3.getString("referenceID"));
		deleteTestReferenceType(testReferenceType.getString("referenceTypeID"));
		deleteTestReferenceSource(testReferenceSource.getString("referenceSourceID"));
	}

	@Test
	public void givenValidReferencessExistFindReferencesShouldReturnEmptyListIfNoDataOnGivenPage() {

		JsonPath testReferenceSource = createTestReferenceSource();
		JsonPath testReferenceType = createTestReferenceType(testReferenceSource);

		JsonPath createdReferenceJson = createTestReference(testReferenceType);

		given()
			.contentType("application/json")
			.param("page", 100)
			.param("size", 1)
			.get("/xref-api/v1/references/")
	   .then()
	   		.assertThat()
	   		.body("_embedded.references", hasSize(0))
			.statusCode(200)
	   		.log()
	   		.all();

		//cleanup
		deleteTestReference(createdReferenceJson.getString("referenceID"));
		deleteTestReferenceType(testReferenceType.getString("referenceTypeID"));
		deleteTestReferenceSource(testReferenceSource.getString("referenceSourceID"));

	}

	@Test
	public void givenSizeIsZeroFindReferencesShouldThrowMandatoryFieldsMissingException() {

		given()
			.contentType("application/json")
			.param("page", 0)
			.param("size", 0)
			.get("/xref-api/v1/references/")

		.then()
			.assertThat()
			.statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED)
			.body("exception", equalTo("com.virginvoyages.exception.MandatoryFieldsMissingException"))
			.log()
			.all();
	}
	
	@Test  
	public void givenEmptyReferenceInFindReferencesTypeAndTargetTypeShouldThrowMandatoryFieldsMissingException() {

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("referenceTypeID", "");
		parameters.put("nativeSourceIDValue", "");
		parameters.put("targetReferenceTypeID", "");

		given()
				.contentType("application/json")
				.body(parameters)
				.post("/xref-api/v1/references/search/findByTypeAndTargetType")

		.then()
				.assertThat().statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED)
				.body("exception",equalTo("com.virginvoyages.exception.MandatoryFieldsMissingException"))
				.log()
				.all();

	}
	
	@Test  
	public void givenEmptyReferenceInFindReferencesTypeShouldThrowMandatoryFieldsMissingException() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("referenceTypeID", "");
		parameters.put("nativeSourceIDValue", "");
	
		given()
				.contentType("application/json")
				.body(parameters)
				.post("/xref-api/v1/references/search/findByType")

		.then()
				.assertThat().statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED)
				.body("exception",equalTo("com.virginvoyages.exception.MandatoryFieldsMissingException"))
				.log()
				.all();

	}

}
