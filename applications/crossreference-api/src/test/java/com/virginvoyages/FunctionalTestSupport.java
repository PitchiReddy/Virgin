package com.virginvoyages;

import static io.restassured.RestAssured.port;

import java.util.UUID;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.helper.Oauth2TokenFeignClient;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class FunctionalTestSupport {

	@Autowired
	private Oauth2TokenFeignClient oauth2TokenFeignClient;
    @LocalServerPort
    int LOCAL_PORT;

    @Before
    public void setUp() {
    	
		final JsonPath jsonResponse = new JsonPath(oauth2TokenFeignClient.getTokenResponse("client_credentials"));
    	final String accessToken = jsonResponse.getString("access_token");
    	port = LOCAL_PORT;
        RestAssured.given()
                .headers("correlationID", UUID.randomUUID().toString(), "Authorization", "Bearer " + accessToken);
        
    }

}
