package com.virginvoyages.crossreference.types;

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

import com.virginvoyages.crossreference.assembly.ReferenceTypesAssembly;
import com.virginvoyages.crossreference.data.repositories.ReferenceRepository;
import com.virginvoyages.crossreference.data.repositories.ReferenceSourceRepository;
import com.virginvoyages.crossreference.data.repositories.ReferenceTypeRepository;
import com.virginvoyages.crossreference.helper.TestDataHelper;
import com.virginvoyages.crossreference.model.ReferenceSource;
import com.virginvoyages.crossreference.model.ReferenceType;
import com.virginvoyages.exception.UnknownException;

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

	@InjectMocks
	private ReferenceTypesController referenceTypesController;

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
	// find by reference Type by name
	@Test
	public void givenAssemblyMethodReturnsValidReferenceTypeGetReferenceTypeByNameShouldSetReferenceTypeDetailsInReponse() throws Exception {
		ReferenceType referenceType = testDataHelper.getReferenceTypeBusinessEntity();

		 given(referenceTypesAssembly.findReferenceTypeByName(referenceType.referenceType()))
			.willReturn(referenceType);
		//Test
		 mvc.perform(
				get("/types/findByName/" + referenceType.referenceType())
				.contentType("application/json"))
				.andExpect(jsonPath("referenceTypeID",equalTo(referenceType.referenceTypeID())))
				.andExpect(jsonPath("referenceType",equalTo(referenceType.referenceType())))
		 		.andExpect(status().isOk());
	}

	@Test
	public void givenAssemblyMethodReturnsNullGetReferenceTypeByNameShouldSetDataNotFoundExceptionInReponse() throws Exception {
		 given(referenceTypesAssembly.findReferenceTypeByName(testDataHelper.getRandomAlphabeticString()))
			.willReturn(null);
		//Test
		 mvc.perform(
				get("/types/findByName/" + testDataHelper.getRandomAlphabeticString())
				.contentType("application/json"))
				.andExpect(status().is(HttpStatus.NOT_FOUND.value()));
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

	//Delete
	@Test
	public void givenAssemblyMethodDoesNotThrowAnyExceptionDeleteReferenceTypeByIdShouldReturnHttpStatusOK() throws Exception {
		given(referenceTypesAssembly.deleteReferenceTypeByID(testDataHelper.getRandomAlphabeticString()))
											.willReturn(true);
		mvc.perform(
				 delete("/types/"+testDataHelper.getRandomAlphabeticString())
				.contentType("application/json"))
		        .andExpect(status().isOk());

	}

	@Test
	public void givenNoReferenceTypeIDInRequestBodyDeleteReferenceTypeIDShouldThrowMandatoryFieldsMissingException() throws Exception{
		mvc.perform(
			 	delete("/types/")
				.contentType("application/json"))
				.andExpect(status().is(HttpStatus.METHOD_NOT_ALLOWED.value()));
	}



	//Update
	@Test
	public void givenRequestBodyHasNoReferenceTypeUpdateReferenceTypeShouldSetMandatoryFieldsMissingExceptionToResponse() throws Exception {

		ReferenceType referenceType = testDataHelper.getReferenceTypeBusinessEntity();

		given(referenceTypesAssembly.updateReferenceType(referenceType)).willReturn(null);

		//Test
		mvc.perform(
				put("/types/")
				.contentType("application/json")
				.content("{ \"referenceTypeID\" : \"anyid\","
						+ "\"referenceSourceID\" : \"anyid\"}"))
			    .andExpect(status().is(HttpStatus.METHOD_NOT_ALLOWED.value()));
	}

	@Test
	public void givenRequestBodyHasNoReferenceSourceIDUpdateReferenceTypeShouldSetMandatoryFieldsMissingExceptionToResponse() throws Exception {
		ReferenceType referenceType = testDataHelper.getReferenceTypeBusinessEntity();

		given(referenceTypesAssembly.updateReferenceType(referenceType)).willReturn(null);


		//Test
		mvc.perform(
				put("/types/")
				.contentType("application/json")
				.content("{ \"referenceTypeID\" : \""+referenceType.referenceTypeID()+"\","
						+ "\"referenceType\" : \"\"}"))
			    .andExpect(status().is(HttpStatus.METHOD_NOT_ALLOWED.value()));
	}

	@Test
	public void givenRequestBodyHasEmptyReferenceTypeIDUpdateReferenceTypeShouldSetMandatoryFieldsMissingExceptionToResponse() throws Exception {

		ReferenceType referenceType = testDataHelper.getReferenceTypeBusinessEntity();

		given(referenceTypesAssembly.updateReferenceType(referenceType)).willReturn(null);
		//Test
		mvc.perform(
				put("/types/")
				.contentType("application/json")
				.content("{ \"referenceTypeID\" : \"\","
						+ "\"referenceType\" : \"dummy\","
						+ "\"referenceSourceID\" : \"dummy\"}"))
			    .andExpect(status().is(HttpStatus.METHOD_NOT_ALLOWED.value()));
	}

	/*@Test
	public void givenAssemblyMethodReturnsUpdatedTypeUpdateReferenceTypeShouldSetUpdatedTypeDetailsToResponse() throws Exception {

		ReferenceSource referenceSource = testDataHelper.getReferenceSourceBusinessEntity();
		ReferenceType referenceType = testDataHelper.getReferenceTypeBusinessEntity(referenceSource);

		//given(referenceTypesAssembly.addReferenceType(referenceType)).willReturn(referenceType);

		given(referenceTypesAssembly.updateReferenceType(referenceType)).willReturn(referenceType);

		//Test
		mvc.perform(
				put("/types/")
				.contentType("application/json")
				.content("{ \"referenceTypeID\" : \""+referenceType.referenceTypeID()+
						"\",\"referenceType\" : \""+referenceType.referenceType()+
						"\",\"referenceSourceID\" : \"dummy\"}"))
				.andExpect(jsonPath("referenceTypeID",equalTo(referenceType.referenceTypeID())))
				.andExpect(jsonPath("referenceType",equalTo(referenceType.referenceType())))
				.andExpect(jsonPath("referenceSourceID",equalTo(referenceType.referenceSourceID())))
				.andExpect(status().isOk());
    }
	*/
	@Test
	public void givenAssemblyMethodReturnsNullUpdateReferenceTypeShouldSetDataUpdationExceptionToResponse() throws Exception {
		ReferenceType referenceType = testDataHelper.getReferenceTypeBusinessEntity();

		given(referenceTypesAssembly.updateReferenceType(referenceType)).willReturn(null);

		//Test
		mvc.perform(
				put("/types/")
				.contentType("application/json")
				.content("{ \"referenceTypeID\" : \""+referenceType.referenceTypeID()+
						"\",\"referenceType\" : \""+referenceType.referenceType()+
						"\",\"referenceSourceID\" : \"dummy\"}"))
		        .andExpect(status().is(HttpStatus.NOT_MODIFIED.value()));
	}

	//Find reference types
	@Test
	public void givenNoValueForPageInRequestParamsFindTypesShouldSetBadRequestCodeInResponse() throws Exception {
		List<ReferenceType> referenceTypesList = new ArrayList<ReferenceType>();
		referenceTypesList.add(testDataHelper.getReferenceTypeBusinessEntity());

		given(referenceTypesAssembly.findTypes(any(PageRequest.class))).willReturn(referenceTypesList);

		mvc.perform(
				get("/types/?size=10")
				.contentType("application/json"))
				.andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
	}

	@Test
	public void givenNoValueForSizeInRequestParamsFindTypesShouldSetBadRequestCodeInResponse() throws Exception {

		List<ReferenceType> referenceTypesList = new ArrayList<ReferenceType>();
		referenceTypesList.add(testDataHelper.getReferenceTypeBusinessEntity());

		given(referenceTypesAssembly.findTypes(any(PageRequest.class))).willReturn(referenceTypesList);

		mvc.perform(
				get("/types/?page=1")
				.contentType("application/json"))
				.andExpect(status().is(HttpStatus.BAD_REQUEST.value()));

	}

	@Test
	public void givenMaxSizeForSizeInRequestParamsFindTypesShouldSetMethodNotAllowedInResponse() throws Exception {
		List<ReferenceType> referenceTypesList = new ArrayList<ReferenceType>();
		referenceTypesList.add(testDataHelper.getReferenceTypeBusinessEntity());

		given(referenceTypesAssembly.findTypes(any(PageRequest.class))).willReturn(referenceTypesList);

		ReflectionTestUtils.setField(referenceTypesController, "referenceTypesAssembly", referenceTypesAssembly);
		mvc = MockMvcBuilders.standaloneSetup(referenceTypesController)
				.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
				.setViewResolvers(new ViewResolver() {
					@Override
					public org.springframework.web.servlet.View resolveViewName(String viewName, Locale locale)
							throws Exception {
						return new MappingJackson2JsonView();
					}
				}).build();

		mvc.perform(
				get("/types/?page=1&size=21")
				.contentType("application/json"))
				.andExpect(status().is(HttpStatus.METHOD_NOT_ALLOWED.value()));

	}
	@Test
	public void givenAssemblyMethodReturnsListOfReferenceTypesFindTypesShouldSetListInResponse() throws Exception {
		List<ReferenceType> referenceTypesList = new ArrayList<ReferenceType>();
		referenceTypesList.add(testDataHelper.getReferenceTypeBusinessEntity());

		given(referenceTypesAssembly.findTypes(any(PageRequest.class))).willReturn(referenceTypesList);

		ReflectionTestUtils.setField(referenceTypesController, "referenceTypesAssembly", referenceTypesAssembly);
		mvc = MockMvcBuilders.standaloneSetup(referenceTypesController)
				.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
				.setViewResolvers(new ViewResolver() {
					@Override
					public org.springframework.web.servlet.View resolveViewName(String viewName, Locale locale)
							throws Exception {
						return new MappingJackson2JsonView();
					}
				}).build();

		mvc.perform(
				get("/types/?page=0&size=1")
				.contentType("application/json"))
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(status().is(HttpStatus.OK.value()));
	}

	@Test
	public void givenAssemblyMethodThrowsUnknownExceptionFindTypesShouldSetInternalServerErrorInResponse()
			throws Exception {

		given(referenceTypesAssembly.findTypes(any(PageRequest.class))).willThrow(new UnknownException());

		ReflectionTestUtils.setField(referenceTypesController, "referenceTypesAssembly", referenceTypesAssembly);
		mvc = MockMvcBuilders.standaloneSetup(referenceTypesController)
				.setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
				.setViewResolvers(new ViewResolver() {
					@Override
					public org.springframework.web.servlet.View resolveViewName(String viewName, Locale locale)
							throws Exception {
						return new MappingJackson2JsonView();
					}
				}).build();

		mvc.perform(
				get("/types/?page=1&size=1")
				.contentType("application/json"))
				.andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()));
	}
}

