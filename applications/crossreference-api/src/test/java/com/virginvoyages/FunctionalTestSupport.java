package com.virginvoyages;


import static io.restassured.RestAssured.port;
import static io.restassured.RestAssured.given;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.virginvoyages.crossreference.model.Audited;
import com.virginvoyages.crossreference.sources.ReferenceSource;
import com.virginvoyages.crossreference.sources.helper.TestReferenceSourceDataHelper;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class FunctionalTestSupport {
	
	@Autowired
	private TestReferenceSourceDataHelper testReferenceSourceDataHelper;
	
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
    
    public ReferenceSource createTestReferenceSource() {
    	
    	ReferenceSource referenceSource = testReferenceSourceDataHelper.getDataForCreateReferenceSource();
    	
    	Map<String, Object> parameters = new HashMap<String, Object>();
    	Audited audited = addAuditDataForCreateResource();
    	referenceSource.auditData(audited);
    	parameters.put("auditData", referenceSource.auditData(audited));
    	parameters.put("referenceSourceID", referenceSource.referenceSourceID());
		parameters.put("referenceSourceName", referenceSource.referenceSourceName());
		parameters.put("inActive", referenceSource.inActive());
				
		given().
		   		contentType("application/json").
		   		body(parameters).
		   		post("/v1/sources").
		   		
		then().
		    statusCode(200);
		 
		return referenceSource;
    }
    

	public Audited addAuditDataForCreateResource(){
		Audited audited = new Audited();
		audited.createDate(LocalDate.now());
		audited.createUser("mockXREFapi");
		audited.updateDate(LocalDate.now());
		audited.updateUser("mockXREFapi");
		return audited;
		
	}
	
	public void deleteTestReferenceSource(String referenceSourceID) {
    	//Cleanup
    	given().
    	   		contentType("application/json").
    	   		delete("/v1/sources/"+referenceSourceID).
    	   		
    	then().
    			statusCode(200);
    }
 
}
