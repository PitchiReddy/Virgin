package com.virginvoyages;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.port;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.crm.data.AccountData;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class FunctionalTestSupport {
    protected RequestSpecification specification;
    @LocalServerPort
    int localPort;

    @Before
    public void setUp() {
        port = localPort;
        specification = RestAssured.given()
                .header("correlationID", UUID.randomUUID().toString());
        //specification.log().everything();
    }
    
    public String createTestSailorAndGetID(AccountData accountData) {
    	Map<String, Object> parameters = new HashMap<String, Object>();
		
		parameters.put("firstName", accountData.firstName());
		parameters.put("lastName", accountData.lastName());
		parameters.put("email", accountData.primaryEmail());
		parameters.put("dateofBirth",accountData.dateofBirth().toString());
		parameters.put("mobileNumber", accountData.mobileNumber());
				
		String sailorId = 
		given().
		   		contentType("application/json").
		   		params(parameters).
		   		get("v1/sailors/findOrCreate").
		   		
		then().
				statusCode(200).
				extract().
				path("id");
		
    	return sailorId;
    }
    
    public void deleteTestSailor(String sailorId) {
    	//Cleanup
    	given().
    	   		contentType("application/json").
    	   		delete("v1/sailors/"+sailorId).
    	   		
    	then().
    			statusCode(200);
    }
    
}
