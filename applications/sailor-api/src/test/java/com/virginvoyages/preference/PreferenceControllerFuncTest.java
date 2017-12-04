package com.virginvoyages.preference;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.not;
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
public class PreferenceControllerFuncTest extends SailorFunctionalTestSupport {

	@Autowired
	private TestDataHelper testDataHelper;

	@Autowired
	private Oauth2TokenFeignClient oauth2TokenFeignClient;
	
	
	private String  getToken() {
		final JsonPath jsonResponse = new JsonPath(oauth2TokenFeignClient.getTokenResponse("client_credentials"));
    	final String accessToken = jsonResponse.getString("access_token");
    	
    	return accessToken;
	}
	
	@Test
	public void givenValidSailorIdWithPreferencesFindSailorPreferencesShouldReturnPreferences() throws Exception {
		
		String sailorID = testDataHelper.getSailorIDWithPreferences();
		
		given()
		.header("Authorization", "Bearer " + getToken())
		.get("/sailor-api/v1/sailors/" + sailorID + "/preferences")
	       .then()
	       .assertThat().statusCode(200)
	       .assertThat().body("_embedded.preferences", not(hasSize(0)))
	       .assertThat().body("_embedded.preferences.category", hasItems("category_1","category_2","category_3"))
	       .assertThat().body("_embedded.preferences.subCategory", hasItems("sub_category_1","sub_category_2","sub_category_3"))
	       .assertThat().body("_embedded.preferences.option", hasItems("preference_1","preference_2","preference_3"))
	       .log()
	       .all();
	}

	@Test
	public void givenValidSailorIdWithoutPreferencesFindSailorPreferencesShouldReturnEmptyList() throws Exception {
		
        String sailorID = testDataHelper.getSailorIDWithoutPreferences();
		
		given()
		.header("Authorization", "Bearer " + getToken())
		.get("/sailor-api/v1/sailors/" + sailorID + "/preferences")
	       .then()
	       .assertThat().statusCode(200)
	       .assertThat().body("_embedded.preferences",hasSize(0))
	       .log()
	       .all();
		
	}
	
	@Test
	public void givenInvalidSailorFindSailorPreferencesShouldReturnEmptyList() throws Exception {
		
        String sailorID = testDataHelper.getInvalidSailorID();
		
        given()
        .header("Authorization", "Bearer " + getToken())
        .get("/sailor-api/v1/sailors/" + sailorID + "/preferences")
	       .then()
	       .assertThat().statusCode(200)
	       .assertThat().body("_embedded.preferences",hasSize(0))
	       .log()
	       .all();
		
	}
	
}
