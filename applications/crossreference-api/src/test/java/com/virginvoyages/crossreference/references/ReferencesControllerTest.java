package com.virginvoyages.crossreference.references;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.virginvoyages.assembly.ReferencesAssembly;
import com.virginvoyages.crossreference.exceptions.DataNotFoundException;
import com.virginvoyages.crossreference.exceptions.DataUpdationException;
import com.virginvoyages.crossreference.helper.TestDataHelper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ReferencesControllerTest {
	@Autowired
	private MockMvc mvc;
	@Autowired
	private TestDataHelper testDataHelper;

	@MockBean(name = "referencesAssembly")
	ReferencesAssembly referencesAssembly;

	@Test 
	public void givenValidReferenceAddReferenceAndShouldReturnReference() throws Exception {
			
		Reference reference = testDataHelper.getReferenceBusinessEntity();
		 
		given(referencesAssembly.addReference(reference)).willReturn(reference);
		
			 //Test
		     mvc.perform(
					post("/references/")
					.contentType("application/json")
			 		.content("{ \"masterID\" : \""+reference.masterID()+
					  		 "\",\"nativeSourceIDValue\" : \""+reference.nativeSourceIDValue()+
					  		"\",\"referenceTypeID\" : \""+reference.referenceTypeID()+
					  		"\",\"referenceID\" : \""+reference.referenceID()))
			 		.andExpect(status().isOk());
       }
	
	@Test 
	public void givenValidReferenceDeleteReferenceByIDShouldDeleteReference() throws Exception {
		
		Reference reference = testDataHelper.getReferenceBusinessEntity();
		
		//Test
		mvc.perform(
				delete("/references/"+ reference.referenceID())
				.contentType("application/json")
		        .content("{ \"referenceID\" : \""+reference.referenceID()+"\"}"))
		        .andExpect(status().isOk());
	}
	
	@Test 
	public void givenValidReferenceUpdateReferenceByIDShouldUpdateReference() throws Exception {
		
		Reference reference = testDataHelper.getReferenceBusinessEntity();
		
		given(referencesAssembly.updateReference(reference))
		.willReturn(reference);
		
		//Test
		mvc.perform(
				put("/references/")
				.param("masterID", "Updated masterID")
				.contentType("application/json")
				.content("{ \"referenceTypeID\" : \""+reference.referenceTypeID()+
						"\",\"masterID\" : \""+reference.masterID()+
						"\",\"nativeSourceIDValue\" : \""+reference.nativeSourceIDValue()+"\"}"))
		        .andExpect(status().isOk());
	}
	
	@Test 
	public void givenInValidReferenceDeleteReferenceByIDShouldThrowDataNotFoundException() throws Exception {
		
		String InvalidReferenceID = testDataHelper.getRandomAlphanumericString();
		doThrow(new DataNotFoundException()).when(referencesAssembly).deleteReferenceByID(InvalidReferenceID);
		 mvc.perform(
				 get("/references/"+InvalidReferenceID)
				.contentType("application/json"))
		        .andExpect(status().isOk());
	}
	
	
	@Test 
	public void givenInValidReferenceSourceIDInRequestBodyeUpdateReferenceSourceByIDShouldThrowbadRequestException() throws Exception {
		
		Reference reference= testDataHelper.getReferenceBusinessEntity();
		given(referencesAssembly.updateReference(reference))
		.willThrow(new DataUpdationException());
		
		mvc.perform(
				put("/references")
				.param("masterID", "Updated masterID")
				.param("nativeSourceIDValue", "nativeSourceIDValue")
				.contentType("application/json"))
				.andExpect(status().isBadRequest());
		
	}
	
	@Test 
	public void givenNoReferenceIDInRequestBodyDeleteReferenceIDShouldThrowMandatoryFieldsMissingException() throws Exception{
		
		mvc.perform(
			 	delete("/references/")
				.contentType("application/json")
				.content("{ \"referenceID\" : \"referenceID\"}"))
		 		.andExpect(status().isMethodNotAllowed());
		}

}
