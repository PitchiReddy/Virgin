package com.virginvoyages.crossreference.types;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.netflix.feign.FeignAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.virginvoyages.assembly.ReferenceTypesAssembly;
import com.virginvoyages.crossreference.helper.MockDataHelper;

@RunWith(SpringRunner.class)
@WebMvcTest(ReferenceTypesController.class)
@ImportAutoConfiguration({ FeignAutoConfiguration.class })
public class ReferenceTypesControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private MockDataHelper mockDataHelper;
	
	
	
	@MockBean(name="referenceTypesAssembly")
	ReferenceTypesAssembly referenceTypesAssembly;
	
	
	@Test 
	public void givenValidReferenceTypeIDGetReferenceTypeByIdShouldReturnReferenceType() throws Exception {
		
		 ReferenceType referenceType = mockDataHelper.getDataForCreateReferenceType();
		 
		 given(referenceTypesAssembly.findReferenceTypeByID(referenceType.referenceTypeID()))
			.willReturn(referenceType);
		//Test
		 mvc.perform(
				get("/types/" + referenceType.referenceTypeID())
				.contentType("application/json"))
				.andExpect(jsonPath("referenceTypeID",equalTo(referenceType.referenceTypeID())))
				.andExpect(jsonPath("referenceName",equalTo(referenceType.referenceName())))
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
		
		ReferenceType referenceType = mockDataHelper.getDataForCreateReferenceType();
		
		mvc.perform(
				delete("/types/"+ referenceType.referenceTypeID())
				.contentType("application/json")
		        .content("{ \"referenceTypeID\" : \""+referenceType.referenceTypeID()+"\"}"))
		        .andExpect(status().isOk());
		
	}
	
	@Test 
	public void givenInvalidReferenceTypeByIDShouldDeleteReferenceTypes() throws Exception {
		
		String invalidReferenceTypeID= mockDataHelper.getInvalidReferenceTypeByID();
		
		mvc.perform(
				delete("/types/"+invalidReferenceTypeID)
				.contentType("application/json")
		        .content("{ \"referenceTypeID\" : \""+invalidReferenceTypeID+"\"}"))
		        .andExpect(status().isOk());
		
	}

	@Test
	public void givenValidReferenceTypeAddReferenceTypeShouldReturnReferenceTypes() throws Exception {

		mvc.perform(post("/types/")
				.contentType("application/json")
				.content(mockDataHelper.createReferenceTypeInJson("RT30", "Activity", "Reservation")))
				.andExpect(status().isOk());
		
	}
	
	@Test 
		public void givenValidReferenceTypeUpdateReferenceTypeByIDShouldUpdateReferenceType() throws Exception {
			
		ReferenceType referenceType = mockDataHelper.getDataForCreateReferenceType();
			
			//Test
			mvc.perform(
					put("/types/"+ referenceType.referenceTypeID())
	  			    .param("auditData", referenceType.referenceTypeID())
					.param("referenceName", "siva")
					.contentType("application/json")
			        .content("{ \"referenceTypeID\" : \""+referenceType.referenceTypeID()+"\"}"))
			        .andExpect(status().isOk());
		}
	
	
	}

