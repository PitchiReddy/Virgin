package com.virginvoyages.crossreference.references;

import static io.restassured.RestAssured.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.HttpStatus;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import com.virginvoyages.crossreference.references.helper.TestReferenceDataHelper;


public class ReferencesControllerTest {
	@Autowired
	private MockMvc mvc; 
	
	@Autowired
	private TestReferenceDataHelper testReferenceDataHelper;

	@Test 
	public void givenValidReferenceIdFindReferenceByIdShouldFindReference() throws Exception{
		
		Reference reference = testReferenceDataHelper.getReferenceByID();
						
		//Test
		mvc.perform(
				get("/v1/references/" + reference.referenceID())
				.contentType("application/json"))
				.andExpect(status().isOk());
	} 
	
	@Test
	public void givenValidReferenceTypeIdDeleteReferenceTypeByIdShouldDeleteReferenceType() throws Exception {
		String referenceID = testReferenceDataHelper.deleteReferenceByID();
	
		    //Test
			mvc.perform(
				delete("/v1/references/" + referenceID).
				contentType("application/json")).
				andExpect(status().isOk());
	}
	
	@Test
	public void givenInvalidSailorIdDeleteSailorByIdShouldThrowDataNotFoundException() throws Exception {
		
		String referenceID = testReferenceDataHelper.deleteReferenceByID();
				
		//Test
		mvc.perform(
				delete("/v1/references/" + referenceID).
				contentType("application/json")).
				andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }
}
