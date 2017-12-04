package com.virginvoyages.booking;

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
import com.virginvoyages.sailor.helper.Oauth2TokenFeignClient;
import com.virginvoyages.sailor.helper.TestDataHelper;

import io.restassured.path.json.JsonPath;

@RunWith(SpringRunner.class)
@ImportAutoConfiguration({ FeignAutoConfiguration.class })
public class BookingControllerFuncTest extends SailorFunctionalTestSupport  {
	@Autowired
	private Oauth2TokenFeignClient oauth2TokenFeignClient;
	
	
	private String  getToken() {
		final JsonPath jsonResponse = new JsonPath(oauth2TokenFeignClient.getTokenResponse("client_credentials"));
    	final String accessToken = jsonResponse.getString("access_token");
    	
    	return accessToken;
    	
	}
	@Autowired
	private TestDataHelper testDataHelper;

	@Test
	public void givenValidSailorIdWithSailingHistoryFindSailingHistoryShouldReturnSailingHistory() throws Exception {
		
		String sailorID = testDataHelper.getSailorIDWithSailingHistory();
		
		given()
		.header("Authorization", "Bearer " + getToken())
		.get("/sailor-api/v1/sailors/" + sailorID + "/sailingHistory")
	       .then()
	       .assertThat().statusCode(200)
	       .assertThat().body("_embedded.bookings.bookedBySailor", hasSize(greaterThan(0)))
	       .assertThat().body("_embedded.bookings.status", notNullValue())
	       .log()
	       .all();
	}

}
