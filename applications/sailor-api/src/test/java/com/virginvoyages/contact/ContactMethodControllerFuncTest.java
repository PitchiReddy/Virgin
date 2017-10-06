package com.virginvoyages.contact;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.cloud.netflix.feign.FeignAutoConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.SailorFunctionalTestSupport;
import com.virginvoyages.sailor.helper.TestDataHelper;

@RunWith(SpringRunner.class)
@ImportAutoConfiguration({ FeignAutoConfiguration.class })
public class ContactMethodControllerFuncTest extends SailorFunctionalTestSupport {
	
	@Autowired
	private TestDataHelper testDataHelper;

	@Test
	public void givenValidSailorIdWithContactMethodsFindSailorContactsShouldReturnContacts() throws Exception {
		
		String sailorID = testDataHelper.getSailorIDWithContactMethods();
		
		given().get("/sailor-api/v1/sailors/" + sailorID + "/contactMethod")
	       .then()
	       .assertThat().statusCode(200)
	       .assertThat().body("_embedded.contactMethods", hasSize(greaterThan(0)))
	       .assertThat().body("_embedded.contactMethods.contactType", notNullValue())
	       .log()
	       .all();
	}
	
	// TODO tests for ContactMethodsController_FT
	
	/*@Test
	public void givenSailorWithSailorIDHasNOContactMethodsFindContactMethodsBySailorShouldReturnEmptyList()
			throws Exception {
		// Mock Setup
		String sailorID = testDataHelper.getSailorIDWithoutContactMethods();

		given().get("/v1/sailors/" + sailorID + "/contactMethod")
	       .then()
	       .assertThat().statusCode(200)
	       .assertThat().body("_embedded.contactMethods", hasSize(0))
	       .log()
	       .all();

	}

	
	 * @Test public void
	 * givenInvalidSailorIdFindSailorContactMethodsShouldThrowSomeException() {
	 * 
	 * }
	 * 
	 * @Test public void
	 * givenNoSailorIdFindSailorContactMethodsShouldThrowSomeException() {
	 * 
	 * }
	 */

}
