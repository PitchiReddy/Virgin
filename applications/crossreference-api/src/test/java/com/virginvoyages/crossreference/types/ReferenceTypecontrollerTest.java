package com.virginvoyages.crossreference.types;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.cloud.netflix.feign.FeignAutoConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.crossreference.types.ReferenceTypesController;
import com.virginvoyages.reference.types.helper.TestReferenceTypeDataHelper;

import org.springframework.test.web.servlet.MockMvc;

import static io.restassured.RestAssured.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(ReferenceTypesController.class)
@ImportAutoConfiguration({ FeignAutoConfiguration.class })
public class ReferenceTypecontrollerTest {

	@Autowired
	private MockMvc mvc; 
	
	@Autowired
	private TestReferenceTypeDataHelper testReferenceTypeDataHelper;
	
	@Test 
	public void givenValidReferenceTypeIdFindReferenceTypeByIdShouldFindReferenceType() throws Exception{
		
		ReferenceType referenceType = testReferenceTypeDataHelper.getReferenceTypeByID();
						
		//Test
		mvc.perform(
				get("http://localhost:8435/v1/types/" + referenceType.referenceTypeID())
				.contentType("application/json"))
				.andExpect(status().isOk());
	} 
	
	@Test
	public void givenValidReferenceTypeIdDeleteReferenceTypeByIdShouldDeleteReferenceType() {
		String referenceTypeID = testReferenceTypeDataHelper.deleteReferenceTypeByID();
		given().
				contentType("application/json").
				delete("/v1/types/" + referenceTypeID).
		then().
				assertThat().statusCode(200).
				log().
				all().
				extract();
	}

}
