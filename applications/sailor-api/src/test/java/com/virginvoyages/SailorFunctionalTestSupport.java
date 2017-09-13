package com.virginvoyages;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import com.virginvoyages.crm.data.AccountData;

public class SailorFunctionalTestSupport extends FunctionalTestSupport {

	public String createTestSailorAndGetID(AccountData accountData) {
		Map<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("firstName", accountData.firstName());
		parameters.put("lastName", accountData.lastName());
		parameters.put("email", accountData.primaryEmail());
		parameters.put("dateofBirth", accountData.dateofBirth().toString());
		parameters.put("mobileNumber", accountData.mobileNumber());

		String sailorId = given().contentType("application/json").params(parameters).get("v1/sailors/findOrCreate").

				then().statusCode(200).extract().path("id");

		return sailorId;
	}

	public void deleteTestSailor(String sailorId) {
		// Cleanup
		given().contentType("application/json").delete("v1/sailors/" + sailorId).

				then().statusCode(200);
	}

}
