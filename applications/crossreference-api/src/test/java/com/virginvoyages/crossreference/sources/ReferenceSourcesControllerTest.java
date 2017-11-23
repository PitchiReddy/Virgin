package com.virginvoyages.crossreference.sources;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.virginvoyages.crossreference.assembly.ReferenceSourcesAssembly;
import com.virginvoyages.crossreference.data.repositories.ReferenceRepository;
import com.virginvoyages.crossreference.data.repositories.ReferenceSourceRepository;
import com.virginvoyages.crossreference.data.repositories.ReferenceTypeRepository;
import com.virginvoyages.crossreference.helper.TestDataHelper;
import com.virginvoyages.exceptions.UnknownException;
import com.virginvoyages.model.crossreference.ReferenceSource;

@RunWith(SpringRunner.class)
@WebMvcTest(value=ReferenceSourcesController.class)
public class ReferenceSourcesControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private TestDataHelper testDataHelper;
		
	@MockBean(name="referenceSourcesAssembly")
    private ReferenceSourcesAssembly referenceSourcesAssembly;
	
	@MockBean(name="referenceSourceRepository")
    private ReferenceSourceRepository referenceSourceRepository;
	
	@MockBean(name="referenceTypeRepository")
    private ReferenceTypeRepository referenceTypeRepository;
	
	@MockBean(name="referenceRepository")
    private ReferenceRepository referenceRepository;
	
	@InjectMocks
	private ReferenceSourcesController referenceSourcesController;
	
	
	//Add Reference Source
	
	@Test
	public void givenRequestBodyHasEmptyReferenceSourceAddReferenceSourceShouldSetMethodNotAllowedStatusToResponse() throws Exception {
		
		ReferenceSource referenceSource = testDataHelper.getReferenceSourceBusinessEntity();
		
		given(referenceSourcesAssembly.addReferenceSource(referenceSource)).willReturn(referenceSource);
		
		//Test
		mvc.perform(
				post("/sources/")
				.contentType("application/json")
				.content("{ \"referenceSourceID\" : \""+referenceSource.referenceSourceID()+
						"\",\"referenceSource\" : \"\"}"))
		        .andExpect(status().is(HttpStatus.METHOD_NOT_ALLOWED.value()));
		        		
	}
	
	@Test
	public void givenRequestBodyHasNoReferenceSourceAddReferenceSourceShouldSetMandatoryFieldsMissingExceptionToResponse() throws Exception {
		
		ReferenceSource referenceSource = testDataHelper.getReferenceSourceBusinessEntity();
		
		given(referenceSourcesAssembly.addReferenceSource(referenceSource)).willReturn(referenceSource);
		
		//Test
		mvc.perform(
				post("/sources/")
				.contentType("application/json")
				.content("{ \"referenceSourceID\" : \""+referenceSource.referenceSourceID()+"\"}"))
		        .andExpect(status().is(HttpStatus.METHOD_NOT_ALLOWED.value()));
	}
	
	@Test
	public void givenAssemblyMethodReturnsNullAddReferenceSourceShouldSetDataInsertExceptionToResponse () throws Exception {
		
		ReferenceSource referenceSource = testDataHelper.getReferenceSourceBusinessEntity();
		
		given(referenceSourcesAssembly.addReferenceSource(referenceSource)).willReturn(null);
				
		//Test
		mvc.perform(
				post("/sources/")
				.contentType("application/json")
				.content("{ \"referenceSourceID\" : \""+referenceSource.referenceSourceID()+
						"\",\"referenceSource\" : \""+referenceSource.referenceSource()+"\"}"))
			    .andExpect(status().is(HttpStatus.NOT_MODIFIED.value()));
	}
	
	@Test
	public void givenAssemblyMethodReturnsReferenceSourceWithIDAddReferenceSourceSetAddedReferenceSourceDetailsToResponse () throws Exception {
		ReferenceSource referenceSource = testDataHelper.getReferenceSourceBusinessEntity();
		
		given(referenceSourcesAssembly.addReferenceSource(referenceSource)).willReturn(referenceSource);
				
		//Test
		mvc.perform(
				post("/sources/")
				.contentType("application/json")
				.content("{ \"referenceSourceID\" : \""+referenceSource.referenceSourceID()+
						"\",\"referenceSource\" : \""+referenceSource.referenceSource()+"\"}"))
				.andExpect(jsonPath("referenceSourceID",equalTo(referenceSource.referenceSourceID())))
				.andExpect(jsonPath("referenceSource",equalTo(referenceSource.referenceSource())))
				.andExpect(jsonPath("inActive",equalTo(referenceSource.inActive())))
		        .andExpect(status().is(HttpStatus.OK.value()));
	}
		
	// Delete Reference Source
	@Test
	public void givenAssemblyMethodDoesNotThrowAnyExceptionDeleteReferenceSourceByIdShouldReturnHttpStatusOK() throws Exception {
		given(referenceSourcesAssembly.deleteReferenceSourceByID(testDataHelper.getRandomAlphabeticString()))
											.willReturn(true);
		mvc.perform(
				 delete("/sources/"+testDataHelper.getRandomAlphabeticString())
				.contentType("application/json"))
		        .andExpect(status().isOk());
		
	}
	
	@Test 
	public void givenNoReferenceSourceIDInRequestBodyDeleteReferenceSourceIDShouldThrowMandatoryFieldsMissingException() throws Exception{
		mvc.perform(
			 	delete("/sources/")
				.contentType("application/json"))
				.andExpect(status().is(HttpStatus.METHOD_NOT_ALLOWED.value()));
	}
	
	// Update
	@Test 
	public void givenEmptyReferenceSourceIDInRequestBodyDeleteReferenceSourceIDShouldThrowMandatoryFieldsMissingException() throws Exception{
		mvc.perform(
			 	delete("/sources/")
				.contentType("application/json")
				.content("{ \"referenceSourceID\" : \"\"}"))
		 		.andExpect(status().is(HttpStatus.METHOD_NOT_ALLOWED.value()));
	}
	
	//Find reference sources
	@Test
	public void givenNoValueForPageInRequestParamsFindSourcesShouldSetBadRequestCodeInResponse() throws Exception {
		List<ReferenceSource> referenceSourceList = new ArrayList<ReferenceSource>();
		referenceSourceList.add(testDataHelper.getReferenceSourceBusinessEntity());
		
		given(referenceSourcesAssembly.findSources(any(PageRequest.class))).willReturn(referenceSourceList);
		
		mvc.perform(
				 get("/sources/?size=10")
				.contentType("application/json"))
			    .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
	}
	
	@Test
	public void givenNoValueForSizeInRequestParamsFindSourcesShouldSetBadRequestCodeInResponse() throws Exception {
		
		List<ReferenceSource> referenceSourceList = new ArrayList<ReferenceSource>();
		referenceSourceList.add(testDataHelper.getReferenceSourceBusinessEntity());
		
		given(referenceSourcesAssembly.findSources(any(PageRequest.class))).willReturn(referenceSourceList);
		
		mvc.perform(
				 get("/sources/?page=1")
				.contentType("application/json"))
			    .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
	}

	
	@Test
	public void givenAssemblyMethodReturnsListOfReferenceSourcesFindSourcesShouldSetListInResponse() throws Exception {
		List<ReferenceSource> referenceSourceList = new ArrayList<ReferenceSource>();
		referenceSourceList.add(testDataHelper.getReferenceSourceBusinessEntity());
		
		given(referenceSourcesAssembly.findSources(any(PageRequest.class))).willReturn(referenceSourceList);
	
		ReflectionTestUtils.setField(referenceSourcesController, "referenceSourcesAssembly", referenceSourcesAssembly);
		mvc=MockMvcBuilders.standaloneSetup(referenceSourcesController)
				.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
	            .setViewResolvers(new ViewResolver() {
	                @Override
	                public org.springframework.web.servlet.View resolveViewName(String viewName, Locale locale) throws Exception {
	                    return new MappingJackson2JsonView();
	                }
	            }).build();
		
		mvc.perform(
				 get("/sources/?page=1&size=1")
				.contentType("application/json"))
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(status().is(HttpStatus.OK.value()));
	}
	
	@Test
	public void givenAssemblyMethodThrowsUnknownExceptionFindSourcesShouldSetInternalServerErrorInResponse() throws Exception {
		given(referenceSourcesAssembly.findSources(any(PageRequest.class))).willThrow(new UnknownException());
		
		ReflectionTestUtils.setField(referenceSourcesController, "referenceSourcesAssembly", referenceSourcesAssembly);
		mvc=MockMvcBuilders.standaloneSetup(referenceSourcesController)
				.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
	            .setViewResolvers(new ViewResolver() {
	                @Override
	                public org.springframework.web.servlet.View resolveViewName(String viewName, Locale locale) throws Exception {
	                    return new MappingJackson2JsonView();
	                }
	            }).build();
		mvc.perform(
				 get("/sources/?page=1&size=1")
				.contentType("application/json"))
				.andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()));
	}

	//Get Reference Source By ID	
	@Test
	public void givenAssemblyReturnsNullGetReferenceSourceByIdShouldThrowDataNotFoundException() throws Exception {
		
		String testReferenceSourceID = testDataHelper.getRandomAlphanumericString();
		given(referenceSourcesAssembly.findReferenceSourceByID(testReferenceSourceID))
			.willReturn(null);
		 
		mvc.perform(
				 get("/sources/"+testReferenceSourceID)
				.contentType("application/json"))
		        .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
	}
		
	@Test
	public void givenPathVariableContainsEmptySpaceGetReferenceSourceByIdShouldThrowMandatoryFieldsMissingException() throws Exception {
		
		String testReferenceSourceID = testDataHelper.getRandomAlphanumericString();
		given(referenceSourcesAssembly.findReferenceSourceByID(testReferenceSourceID))
			.willReturn(null);
		 
		mvc.perform(
				 get("/sources/  ")
				.contentType("application/json"))
		        .andExpect(status().is(HttpStatus.METHOD_NOT_ALLOWED.value()));
	}
	
	
	@Test 
	public void givenAssemblyReturnsValidReferenceSourceGetReferenceSourceByIdShouldSetReferenceSourceDetailsInResponse() throws Exception {
		
		ReferenceSource referenceSource = testDataHelper.getReferenceSourceBusinessEntity();
		 
		 given(referenceSourcesAssembly.findReferenceSourceByID(referenceSource.referenceSourceID()))
			.willReturn(referenceSource);
		
		 //Test
		 mvc.perform(
				 get("/sources/" + referenceSource.referenceSourceID())
				.contentType("application/json"))
				.andExpect(jsonPath("referenceSourceID",equalTo(referenceSource.referenceSourceID())))
				.andExpect(jsonPath("referenceSource",equalTo(referenceSource.referenceSource())))
				.andExpect(jsonPath("inActive",equalTo(referenceSource.inActive())))
		 		.andExpect(status().isOk());
	}	
	
	// Find By Name
	@Test 
	public void givenAssemblyReturnsValidReferenceSourceNameGetReferenceSourceByNameShouldSetReferenceSourceDetailsInResponse() throws Exception {
		ReferenceSource referenceSource = testDataHelper.getReferenceSourceBusinessEntity();
		 
		 given(referenceSourcesAssembly.findReferenceSourceByName(referenceSource.referenceSource()))
			.willReturn(referenceSource);
		
		 //Test
		 mvc.perform(
				 get("/sources/findByName/" + referenceSource.referenceSource())
				.contentType("application/json"))
				.andExpect(jsonPath("referenceSourceID",equalTo(referenceSource.referenceSourceID())))
				.andExpect(jsonPath("referenceSource",equalTo(referenceSource.referenceSource())))
				.andExpect(jsonPath("inActive",equalTo(referenceSource.inActive())))
		 		.andExpect(status().isOk());
	}	
	
	@Test
	public void givenAssemblyReturnsNullGetReferenceSourceByNameShouldThrowDataNotFoundException() throws Exception {
		String testReferenceSource = testDataHelper.getRandomAlphanumericString();
		given(referenceSourcesAssembly.findReferenceSourceByName(testReferenceSource)).willReturn(null);

		mvc.perform(
				get("/sources/findByName/" + testReferenceSource)
				.contentType("application/json"))
				.andExpect(status().is(HttpStatus.NOT_FOUND.value()));
	}
	
	//Update Source
	@Test
	public void givenRequestBodyHasEmptyReferenceSourceUpdateReferenceSourceShouldSetMandatoryFieldsMissingExceptionToResponse() throws Exception {
		ReferenceSource referenceSource = testDataHelper.getReferenceSourceBusinessEntity();
		
		given(referenceSourcesAssembly.updateReferenceSource(referenceSource)).willReturn(null);
				
		//Test
		mvc.perform(
				put("/sources/")
				.contentType("application/json")
				.content("{ \"referenceSourceID\" : \""+referenceSource.referenceSourceID()+"\","
						+ "\"referenceSource\" : \"\"}"))
			    .andExpect(status().is(HttpStatus.METHOD_NOT_ALLOWED.value()));
	}
	
	@Test
	public void givenRequestBodyHasNoReferenceSourceUpdateReferenceSourceShouldSetMandatoryFieldsMissingExceptionToResponse() throws Exception {
		ReferenceSource referenceSource = testDataHelper.getReferenceSourceBusinessEntity();
		
		given(referenceSourcesAssembly.updateReferenceSource(referenceSource)).willReturn(null);
				
		//Test
		mvc.perform(
				put("/sources/")
				.contentType("application/json")
				.content("{ \"referenceSourceID\" : \""+referenceSource.referenceSourceID()+"\"}"))
			    .andExpect(status().is(HttpStatus.METHOD_NOT_ALLOWED.value()));
	}
	
	@Test
	public void givenRequestBodyHasEmptyReferenceSourceIDUpdateReferenceSourceShouldSetMandatoryFieldsMissingExceptionToResponse() throws Exception {
		ReferenceSource referenceSource = testDataHelper.getReferenceSourceBusinessEntity();
		
		given(referenceSourcesAssembly.updateReferenceSource(referenceSource)).willReturn(null);
				
		//Test
		mvc.perform(
				put("/sources/")
				.contentType("application/json")
				.content("{ \"referenceSourceID\" : \"\","
						+ "\"referenceSource\" : \""+referenceSource.referenceSource()+"\"}"))
			    .andExpect(status().is(HttpStatus.METHOD_NOT_ALLOWED.value()));
	}
	
	@Test
	public void givenRequestBodyHasNoReferenceSourceIDUpdateReferenceSourceShouldSetMandatoryFieldsMissingExceptionToResponse() throws Exception {
		ReferenceSource referenceSource = testDataHelper.getReferenceSourceBusinessEntity();
		
		given(referenceSourcesAssembly.updateReferenceSource(referenceSource)).willReturn(null);
				
		//Test
		mvc.perform(
				put("/sources/")
				.contentType("application/json")
				.content("{ \"referenceSource\" : \""+referenceSource.referenceSource()+"\"}"))
			    .andExpect(status().is(HttpStatus.METHOD_NOT_ALLOWED.value()));
	}
	
	@Test
	public void givenAssemblyMethodReturnsUpdatedSourceUpdateReferenceSourceShouldSetUpdatedSourceDetailsToResponse() throws Exception {
		ReferenceSource referenceSource = testDataHelper.getReferenceSourceBusinessEntity();
		
		given(referenceSourcesAssembly.updateReferenceSource(referenceSource))
		.willReturn(referenceSource);
		
		//Test
		mvc.perform(
				put("/sources/")
				.contentType("application/json")
				.content("{ \"referenceSourceID\" : \""+referenceSource.referenceSourceID()+
						"\",\"referenceSource\" : \""+referenceSource.referenceSource()+"\"}"))
		        .andExpect(status().isOk());
	}
	
	@Test
	public void givenAssemblyMethodReturnsNullUpdateReferenceSourceShouldSetDataUpdationExceptionToResponse() throws Exception {
		ReferenceSource referenceSource = testDataHelper.getReferenceSourceBusinessEntity();
		
		given(referenceSourcesAssembly.updateReferenceSource(testDataHelper.getReferenceSourceBusinessEntity()))
		.willReturn(null);
		
		//Test
		mvc.perform(
				put("/sources/")
				.contentType("application/json")
				.content("{ \"referenceSourceID\" : \""+referenceSource.referenceSourceID()+
						"\",\"referenceSource\" : \""+referenceSource.referenceSource()+"\"}"))
		        .andExpect(status().is(HttpStatus.NOT_MODIFIED.value()));
	}
		
}
