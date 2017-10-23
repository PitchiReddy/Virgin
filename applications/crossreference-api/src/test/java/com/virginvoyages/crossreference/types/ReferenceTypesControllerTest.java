package com.virginvoyages.crossreference.types;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.virginvoyages.assembly.ReferenceTypesAssembly;
import com.virginvoyages.crossreference.helper.TestDataHelper;
import com.virginvoyages.crossreference.sources.ReferenceSource;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ReferenceTypesControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private TestDataHelper testDataHelper;
	
	
	@MockBean(name="referenceTypesAssembly")
	ReferenceTypesAssembly referenceTypesAssembly;
	
	
	@Test 
	public void givenValidReferenceTypeIDGetReferenceTypeByIdShouldReturnReferenceType() throws Exception {
		
		 ReferenceType referenceType = testDataHelper.getReferenceTypeBusinessEntity();
		 
		 given(referenceTypesAssembly.findReferenceTypeByID(referenceType.referenceTypeID()))
			.willReturn(referenceType);
		//Test
		 mvc.perform(
				get("/types/" + referenceType.referenceTypeID())
				.contentType("application/json"))
				.andExpect(jsonPath("referenceTypeID",equalTo(referenceType.referenceTypeID())))
				//.andExpect(jsonPath("referenceName",equalTo(referenceType.referenceName())))
				.andExpect(jsonPath("referenceType",equalTo(referenceType.referenceType())))
		 		.andExpect(status().isOk());

	}		
	
		
	/*@Test 
	public void  givenInValidReferenceTypeIDShouldThrowDataNotFoundException() throws Exception {
		
		String invalidReferenceTypeID= mockDataHelper.getInvalidReferenceTypeByID();
		//Test
		mvc.perform(
				get("/types/" + invalidReferenceTypeID)
				.contentType("application/json"))
		        .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
	}*/
	
	@Test 
	public void givenvalidReferenceTypeByIDShouldDeleteReferenceTypes() throws Exception {
		
		ReferenceType referenceType = testDataHelper.getReferenceTypeBusinessEntity();
		
		mvc.perform(
				delete("/types/"+ referenceType.referenceTypeID())
				.contentType("application/json")
		        .content("{ \"referenceTypeID\" : \""+referenceType.referenceTypeID()+"\"}"))
		        .andExpect(status().isOk());
		
	}
	
	@Test 
	public void givenInvalidReferenceTypeByIDShouldDeleteReferenceTypes() throws Exception {
		
		String invalidReferenceTypeID= testDataHelper.getRandomAlphabeticString();
		
		mvc.perform(
				delete("/types/"+invalidReferenceTypeID)
				.contentType("application/json")
		        .content("{ \"referenceTypeID\" : \""+invalidReferenceTypeID+"\"}"))
		        .andExpect(status().isOk());
		
	}

	/*@Test
	public void givenValidReferenceTypeAddReferenceTypeShouldReturnReferenceTypes() throws Exception {

		mvc.perform(post("/types/")
				.contentType("application/json")
				.content(mockDataHelper.createReferenceTypeInJson("RT30", "Activity", "Reservation")))
				.andExpect(status().isOk());
		
	}*/
	
	@Test 
	public void givenValidReferenceTypeUpdateReferenceTypeByIDShouldUpdateReferenceType() throws Exception {
			
		ReferenceType referenceType = testDataHelper.getReferenceTypeBusinessEntity();
			
		given(referenceTypesAssembly.updateReferenceType(referenceType))
		.willReturn(referenceType);
		
			//Test
			mvc.perform(
					put("/types/")
	  			    .contentType("application/json")
			        //.content("{ \"referenceTypeID\" : \""+referenceType.referenceTypeID()+"\"}"))
	  			    .content("{ \"referenceTypeID\" : \""+referenceType.referenceTypeID()+
							"\",\"referenceType\" : \""+referenceType.referenceType()+"\"}"))
			        .andExpect(status().isOk());
			
	}
}

