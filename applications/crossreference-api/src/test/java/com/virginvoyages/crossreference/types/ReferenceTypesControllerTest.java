package com.virginvoyages.crossreference.types;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.virginvoyages.crossreference.assembly.ReferenceTypesAssembly;
import com.virginvoyages.crossreference.data.repositories.ReferenceRepository;
import com.virginvoyages.crossreference.data.repositories.ReferenceSourceRepository;
import com.virginvoyages.crossreference.data.repositories.ReferenceTypeRepository;
import com.virginvoyages.crossreference.helper.TestDataHelper;
import com.virginvoyages.model.crossreference.ReferenceSource;
import com.virginvoyages.model.crossreference.ReferenceType;


@RunWith(SpringRunner.class)
@WebMvcTest(value=ReferenceTypesController.class)
public class ReferenceTypesControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private TestDataHelper testDataHelper;
	
	@MockBean(name="referenceTypesAssembly")
    private ReferenceTypesAssembly referenceTypesAssembly;
	
	@MockBean(name="referenceTypeRepository")
    private ReferenceTypeRepository referenceTypeRepository;
		
	@MockBean(name="referenceSourceRepository")
    private ReferenceSourceRepository referenceSourceRepository;
		
	@MockBean(name="referenceRepository")
    private ReferenceRepository referenceRepository;
	
	//Add
	@Test
	public void givenRequestBodyHasEmptyReferenceTypeAddReferenceTypeShouldSetMethodNotAllowedStatusToResponse() throws Exception {
		
		ReferenceSource referenceSource = testDataHelper.getReferenceSourceBusinessEntity();
		ReferenceType referenceType = testDataHelper.getReferenceTypeBusinessEntity(referenceSource);
				
		given(referenceTypesAssembly.addReferenceType(referenceType)).willReturn(referenceType);
		
		//Test
		mvc.perform(
				post("/types/")
				.contentType("application/json")
				.content("{ \"referenceTypeID\" : \""+referenceType.referenceTypeID()+"\","
						+ "\"referenceType\" : \"\","
						+ "\"referenceSourceID\" : \""+referenceSource.referenceSourceID()+"\"}"))
		        .andExpect(status().is(HttpStatus.METHOD_NOT_ALLOWED.value()));
	}
	
	@Test
	public void givenRequestBodyHasNoReferenceTypeAddReferenceTypeShouldSetMethodNotAllowedStatusToResponse() throws Exception {
		
		ReferenceSource referenceSource = testDataHelper.getReferenceSourceBusinessEntity();
		ReferenceType referenceType = testDataHelper.getReferenceTypeBusinessEntity(referenceSource);
		
		given(referenceTypesAssembly.addReferenceType(referenceType)).willReturn(referenceType);
		
		//Test
		mvc.perform(
				post("/types/")
				.contentType("application/json")
				.content("{ \"referenceTypeID\" : \""+referenceType.referenceTypeID()+
							"\",\"referenceSourceID\" : \""+referenceSource.referenceSourceID()+"\"}"))
		        .andExpect(status().is(HttpStatus.METHOD_NOT_ALLOWED.value()));
	}
	
	@Test
	public void givenRequestBodyHasEmptyReferenceSourceIDAddReferenceTypeShouldSetMethodNotAllowedStatusToResponse() throws Exception {
		
		ReferenceSource referenceSource = testDataHelper.getReferenceSourceBusinessEntity();
		ReferenceType referenceType = testDataHelper.getReferenceTypeBusinessEntity(referenceSource);
		
		given(referenceTypesAssembly.addReferenceType(referenceType)).willReturn(referenceType);
		
		//Test
		mvc.perform(
				post("/types/")
				.contentType("application/json")
				.content("{ \"referenceTypeID\" : \""+referenceType.referenceTypeID()+
						"\",\"referenceType\" : \""+referenceType.referenceType()+
						"\",\"referenceSourceID\" : \"\"}"))
		        .andExpect(status().is(HttpStatus.METHOD_NOT_ALLOWED.value()));
		        		
	}
	
	@Test
	public void givenRequestBodyHasNoReferenceSourceIDAddReferenceTypeShouldSetMethodNotAllowedStatusToResponse() throws Exception {
		
		ReferenceType referenceType = testDataHelper.getReferenceTypeBusinessEntity();
		
		given(referenceTypesAssembly.addReferenceType(referenceType)).willReturn(referenceType);
		
		//Test
		mvc.perform(
				post("/types/")
				.contentType("application/json")
				.content("{ \"referenceTypeID\" : \""+referenceType.referenceTypeID()+
						"\",\"referenceType\" : \""+referenceType.referenceType()+"\"}"))
		        .andExpect(status().is(HttpStatus.METHOD_NOT_ALLOWED.value()));
	}
		
	
	@Test
	public void givenAssemblyMethodReturnsNullAddReferenceTypeShouldSetDataInsertExceptionToResponse () throws Exception {
		
		ReferenceSource referenceSource = testDataHelper.getReferenceSourceBusinessEntity();
		ReferenceType referenceType = testDataHelper.getReferenceTypeBusinessEntity(referenceSource);
		
		given(referenceTypesAssembly.addReferenceType(referenceType)).willReturn(null);
		
		//Test
		mvc.perform(
				post("/types/")
				.contentType("application/json")
				.content("{ \"referenceTypeID\" : \""+referenceType.referenceTypeID()+
						"\",\"referenceType\" : \""+referenceType.referenceType()+
						"\",\"referenceSourceID\" : \""+referenceSource.referenceSourceID()+"\"}"))
				.andExpect(status().is(HttpStatus.NOT_MODIFIED.value()));
	}
	
	@Test
	public void givenAssemblyMethodReturnsReferenceTypeWithIDAddReferenceTypeShouldSetAddedReferenceTypeDetailsToResponse () throws Exception {
		
		ReferenceSource referenceSource = testDataHelper.getReferenceSourceBusinessEntity();
		ReferenceType referenceType = testDataHelper.getReferenceTypeBusinessEntity(referenceSource);
		
		given(referenceTypesAssembly.addReferenceType(referenceType)).willReturn(referenceType);
				
		//Test
		mvc.perform(
				post("/types/")
				.contentType("application/json")
				.content("{ \"referenceTypeID\" : \""+referenceType.referenceTypeID()+
						"\",\"referenceType\" : \""+referenceType.referenceType()+
						"\",\"referenceSourceID\" : \""+referenceSource.referenceSourceID()+"\"}"))
				.andExpect(jsonPath("referenceSourceID",equalTo(referenceSource.referenceSourceID())))
				.andExpect(jsonPath("referenceTypeID",equalTo(referenceType.referenceTypeID())))
				.andExpect(jsonPath("referenceType",equalTo(referenceType.referenceType())))
				.andExpect(status().is(HttpStatus.OK.value()));
	}
	
	// Find By ID
	@Test
	public void givenReferenceTypeIDNotPresentInPathVariableGetReferenceTypeByIDShouldThrowMandatoryFieldsMissingException() throws Exception {
		mvc.perform(
				get("/types/")
				.contentType("application/json"))
				.andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
	}
	
	@Test 
	public void givenAssemblyMethodReturnsValidReferenceTypeGetReferenceTypeByIdShouldSetReferenceTypeDetailsInReponse() throws Exception {
		
		 ReferenceType referenceType = testDataHelper.getReferenceTypeBusinessEntity();
		 
		 given(referenceTypesAssembly.findReferenceTypeByID(referenceType.referenceTypeID()))
			.willReturn(referenceType);
		//Test
		 mvc.perform(
				get("/types/" + referenceType.referenceTypeID())
				.contentType("application/json"))
				.andExpect(jsonPath("referenceTypeID",equalTo(referenceType.referenceTypeID())))
				.andExpect(jsonPath("referenceType",equalTo(referenceType.referenceType())))
		 		.andExpect(status().isOk());

	}	
	
	@Test 
	public void givenAssemblyMethodReturnsNullGetReferenceTypeByIdShouldSetDataNotFoundExceptionInReponse() throws Exception {
		
		 given(referenceTypesAssembly.findReferenceTypeByID(testDataHelper.getRandomAlphabeticString()))
			.willReturn(null);
		//Test
		 mvc.perform(
				get("/types/" + testDataHelper.getRandomAlphabeticString())
				.contentType("application/json"))
				.andExpect(status().is(HttpStatus.NOT_FOUND.value()));

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
							"\",\"referenceType\" : \""+referenceType.referenceType()+
							"\",\"referenceSourceID\" : \""+referenceType.referenceSourceID()+"\"}"))
			        .andExpect(status().isOk());
			
	}
}

