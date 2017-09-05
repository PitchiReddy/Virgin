package com.virginvoyages.sailor;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import com.virginvoyages.assembly.SailorAssembly;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.cloud.netflix.feign.FeignAutoConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.FunctionalTestSupport;
import com.virginvoyages.crm.data.AccountData;

@RunWith(SpringRunner.class)
@ImportAutoConfiguration({ FeignAutoConfiguration.class })
public class SailorControllerFuncTest extends FunctionalTestSupport {


	private String sailorID = "0010n000003gDGbAAM";

	@Test
	public void validateSailorResponse() {
		given().
				contentType("application/json").
				get("/v1/sailors/"+sailorID).
		then().
				statusCode(200).
				log().
				all();
	}

	@Test
	public void testSailorProfile() {
		given().
		 		expect().
		 		statusCode(200).
		 		body("prefix", equalTo("Mr.")).
		 		body("firstName", equalTo("Danielle")).
		 		body("lastName", equalTo("A")).
		 		//body("preferredName", equalTo("Danielle")).
		 		body("occupation", equalTo("Student")).
		 		//body("tribe", equalTo("Explorer")).
		 		body("id", equalTo(sailorID)).
		 when()
				.get("/v1/sailors/"+sailorID);
	}

	/*@Test
	public void testFindSailorDetails() {
		String firstName="PreddyF1";
		String lastName= "PreddyL1";
		String email = "pb@test.com";
		String dateofbirth = "2017-08-03";
		LocalDate localDate = LocalDate.parse(dateofbirth);

		given().
				contentType("application/json").
				get("/v1/sailors/find?firstName="+firstName+"&"+"lastName="+lastName+"&"+"email="+email+"&"+
				"dateofBirth ="+localDate).
		then().
				statusCode(200).
				log().
				all();


	}*/

	@Test
	public void deleteSailorById(){

        String firstName="PreddyF1";
        String lastName= "PreddyL1";
        String email = "pb@test.com";
        String dateofbirth = "2017-08-03";

        String sailorID =
        given().
                param("firstName", firstName).
                param("lastName", lastName).
                param("email", email).
                param("dateofBirth", dateofbirth).
                get("/v1/sailors/findOrCreate").
        then().
                statusCode(200).
        extract().
                path("id");

		given().
				contentType("application/json").
				delete("/v1/sailors/" + sailorID).
		then().
				statusCode(200);
	}

}
