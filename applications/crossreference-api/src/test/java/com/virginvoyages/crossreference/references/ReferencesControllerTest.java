package com.virginvoyages.crossreference.references;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
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
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.virginvoyages.crossreference.assembly.ReferencesAssembly;
import com.virginvoyages.crossreference.data.repositories.ReferenceRepository;
import com.virginvoyages.crossreference.data.repositories.ReferenceSourceRepository;
import com.virginvoyages.crossreference.data.repositories.ReferenceTypeRepository;
import com.virginvoyages.crossreference.helper.TestDataHelper;
import com.virginvoyages.exception.DataUpdationException;
import com.virginvoyages.exception.UnknownException;
import com.virginvoyages.model.crossreference.Reference;


@RunWith(SpringRunner.class)
@WebMvcTest(value=ReferencesController.class)
public class ReferencesControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private TestDataHelper testDataHelper;

	@MockBean(name="referencesAssembly")
	ReferencesAssembly referencesAssembly;

	@MockBean(name="referenceRepository")
    private ReferenceRepository referenceRepository;

	@MockBean(name="referenceSourceRepository")
    private ReferenceSourceRepository referenceSourceRepository;

	@MockBean(name="referenceTypeRepository")
    private ReferenceTypeRepository referenceTypeRepository;

	@InjectMocks
	private ReferencesController referencesController;

	@Test
	public void givenValidReferenceIDGetReferenceByIdShouldReturnReference() throws Exception {

		Reference reference = testDataHelper.getReferenceBusinessEntity();

		given(referencesAssembly.findReferenceByID(reference.referenceID())).willReturn(reference);

		 //Test
		 mvc.perform(
				get("/references/" + reference.referenceID())
				.contentType("application/json"))
				.andExpect(jsonPath("referenceID",equalTo(reference.referenceID())))
				.andExpect(jsonPath("nativeSourceIDValue",equalTo(reference.nativeSourceIDValue())))
		 		.andExpect(status().isOk());

	}

	@Test
	public void givenAssemblyReturnsNullGetReferenceByIdShouldThrowDataNotFoundException() throws Exception {

		String testReferenceID = testDataHelper.getRandomAlphanumericString();
		given(referencesAssembly.findReferenceByID(testReferenceID))
			.willReturn(null);
		//Test
		mvc.perform(
				 get("/sources/"+testReferenceID)
				.contentType("application/json"))
		        .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
	}

	@Test
	public void givenValidReferenceAddReferenceAndShouldReturnReference() throws Exception {

		Reference reference = testDataHelper.getReferenceBusinessEntity();

		given(referencesAssembly.addReference(reference)).willReturn(reference);

			 //Test
		     mvc.perform(
					post("/references")
					.contentType("application/json")
			 		.content("{ \"masterID\" : \""+reference.masterID()+
					  		 "\",\"nativeSourceIDValue\" : \""+reference.nativeSourceIDValue()+
					  		"\",\"referenceTypeID\" : \""+reference.referenceTypeID()+
					  		"\",\"referenceID\" : \""+reference.referenceID()+"\"}"))
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

/*	@Test
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
*/
	/*@Test
	public void givenInValidReferenceDeleteReferenceByIDShouldThrowDataNotFoundException() throws Exception {

		String InvalidReferenceID = testDataHelper.getRandomAlphanumericString();
		doThrow(new DataNotFoundException()).when(referencesAssembly).deleteReferenceByID(InvalidReferenceID);
		 mvc.perform(
				 get("/references/"+InvalidReferenceID)
				.contentType("application/json"))
		        .andExpect(status().isOk());
	}*/


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

	@Test
	public void givenRequestBodyHasEmptyNativeSourceIDValueUpdateReferenceShouldSetMandatoryFieldsMissingExceptionToResponse() throws Exception {
		Reference reference= testDataHelper.getReferenceBusinessEntity();

		given(referencesAssembly.updateReference(reference)).willReturn(null);

		//Test
		mvc.perform(
				put("/references/")
				.contentType("application/json")
				.content("{ \"referenceID\" : \""+reference.referenceID()+"\","
						+ "\"nativeSourceIDValue\" : \"\"}"))
			    .andExpect(status().is(HttpStatus.METHOD_NOT_ALLOWED.value()));
	}

	@Test
	public void givenRequestBodyHasNoReferenceIDUpdateReferenceShouldSetMandatoryFieldsMissingExceptionToResponse() throws Exception {
		Reference reference= testDataHelper.getReferenceBusinessEntity();

		given(referencesAssembly.updateReference(reference)).willReturn(null);

		//Test
		mvc.perform(
				put("/references/")
				.contentType("application/json")
				.content("{ \"nativeSourceIDValue\" : \""+reference.nativeSourceIDValue()+"\"}"))
			    .andExpect(status().is(HttpStatus.METHOD_NOT_ALLOWED.value()));
	}

	@Test
	public void givenRequestBodyHasEmptyReferenceIDUpdateReferenceShouldSetMandatoryFieldsMissingExceptionToResponse() throws Exception {
		Reference reference= testDataHelper.getReferenceBusinessEntity();

		given(referencesAssembly.updateReference(reference)).willReturn(null);

		//Test
		mvc.perform(
				put("/sources/")
				.contentType("application/json")
				.content("{ \"referenceID\" : \"\","
						+ "\"nativeSourceIDValue\" : \""+reference.nativeSourceIDValue()+"\"}"))
			    .andExpect(status().is(HttpStatus.METHOD_NOT_ALLOWED.value()));
	}

	//Find All
	@Test
	public void givenNoValueForPageInRequestParamsFindReferencesShouldSetBadRequestCodeInResponse() throws Exception {

		List<Reference> referenceList = new ArrayList<Reference>();
		referenceList.add(testDataHelper.getReferenceBusinessEntity());

		given(referencesAssembly.findReferences(new PageRequest(0, 10))).willReturn(referenceList);

		//Test
		mvc.perform(
				 get("/references/?size=1")
				.contentType("application/json"))
			    .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
	}

	@Test
	public void givenNoValueForSizeInRequestParamsFindReferencesShouldSetBadRequestCodeInResponse() throws Exception {

		List<Reference> referenceList = new ArrayList<Reference>();
		referenceList.add(testDataHelper.getReferenceBusinessEntity());

		given(referencesAssembly.findReferences(new PageRequest(0, 10))).willReturn(referenceList);

		//Test
		mvc.perform(
				 get("/references/?page=1")
				.contentType("application/json"))
			    .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
	}

	@Test
	public void givenAssemblyMethodReturnsListOfReferencesFindReferencesShouldSetReferencesInResponse() throws Exception {
		List<Reference> referenceList = new ArrayList<Reference>();
		referenceList.add(testDataHelper.getReferenceBusinessEntity());
		referenceList.add(testDataHelper.getReferenceBusinessEntity());
		given(referencesAssembly.findReferences(new PageRequest(1, 10))).willReturn(referenceList);

		ReflectionTestUtils.setField(referencesController, "referencesAssembly", referencesAssembly);
		mvc = MockMvcBuilders.standaloneSetup(referencesController)
				.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
				.setViewResolvers(new ViewResolver() {
					public View resolveViewName(String viewName, Locale locale) throws Exception {
						return new MappingJackson2JsonView();
					}
				}).build();


		mvc.perform(
				get("/references?page=1&size=10")
				.contentType("application/json"))
		        .andExpect(jsonPath("$._embedded.references", hasSize(2)))
		        .andExpect(jsonPath("$.page.size", equalTo(10)))
		        .andExpect(jsonPath("$.page.number", equalTo(1)))
				.andExpect(status().isOk())
				.andReturn();
	}

	@Test
	public void givenAssemblyMethodThrowsUnknownExceptionFindTypesShouldSetInternalServerErrorInResponse()
			throws Exception {

		given(referencesAssembly.findReferences(new PageRequest(1, 10))).willThrow(new UnknownException());

		ReflectionTestUtils.setField(referencesController, "referencesAssembly", referencesAssembly);
		mvc = MockMvcBuilders.standaloneSetup(referencesController)
				.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
				.setViewResolvers(new ViewResolver() {
					public View resolveViewName(String viewName, Locale locale) throws Exception {
						return new MappingJackson2JsonView();
					}
				}).build();

		mvc.perform(
				get("/references?page=1&size=10")
				.contentType("application/json"))
				.andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()));
	}
}
