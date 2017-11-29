package com.virginvoyages;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.port;

import java.util.UUID;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class FunctionalTestSupport {

    @LocalServerPort
    int LOCAL_PORT;

    @Before
    public void setUp() {
    	
		final String response = given().auth().preemptive().basic("4f177c76-1b53-4ad3-89b5-6c75d1745526", "tDJJSjWS2hAawZd3")
    			.post("http://10.3.100.88:31362/svc/identityaccessmanagement-service/oauth/token?grant_type=client_credentials").asString();  	
		final JsonPath jsonResponse = new JsonPath(response);
    	final String accessToken = jsonResponse.get("access_token");
    	port = LOCAL_PORT;
        RestAssured.given()
                .headers("correlationID", UUID.randomUUID().toString(), "Authorization", "Bearer " + accessToken);
        
    }

}
