package com.virginvoyages;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.virginvoyages.crm.data.AccountData;
import com.virginvoyages.sailor.helper.Oauth2TokenFeignClient;

import io.restassured.path.json.JsonPath;

import com.virginvoyages.FunctionalTestSupport;

public class SailorFunctionalTestSupport extends FunctionalTestSupport {
	@Autowired
	private Oauth2TokenFeignClient oauth2TokenFeignClient;
	
	
	private String  getToken() {
		final JsonPath jsonResponse = new JsonPath(oauth2TokenFeignClient.getTokenResponse("client_credentials"));
    	final String accessToken = jsonResponse.getString("access_token");
    	
    	return accessToken;
    	
	}
	@Test
    public void contextLoads() {
    }
	
	public String createTestSailorAndGetID(AccountData accountData) {
		Map<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("firstName", accountData.firstName());
		parameters.put("lastName", accountData.lastName());
		parameters.put("email", accountData.primaryEmail());
		parameters.put("dateofBirth", accountData.dateofBirth().toString());
		parameters.put("mobileNumber", accountData.mobileNumber());

		String sailorId = 
				given()
					.contentType("application/json")
					.header("Authorization", "Bearer " + getToken())
					.params(parameters)
					.get("/sailor-api/v1/sailors/findOrCreate").
				then()
					.statusCode(200)
					.extract()
					.path("id");

		return sailorId;
	}

	public void deleteTestSailor(String sailorId) {
		// Cleanup
		given()
			.contentType("application/json")
			.header("Authorization", "Bearer " + getToken())
			.delete("/sailor-api/v1/sailors/" + sailorId).

		then()
			.statusCode(200);
	}

}
