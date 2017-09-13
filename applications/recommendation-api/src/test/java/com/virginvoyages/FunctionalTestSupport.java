package com.virginvoyages;

import static io.restassured.RestAssured.port;

import java.util.UUID;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
		specification = RestAssured.given().header("correlationID", UUID.randomUUID().toString());
		// specification.log().everything();
	}

}
