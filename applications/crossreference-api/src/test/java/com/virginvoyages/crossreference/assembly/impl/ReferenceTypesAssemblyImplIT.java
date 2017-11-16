package com.virginvoyages.crossreference.assembly.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.exceptions.DataInsertionException;
import com.virginvoyages.exceptions.DataNotFoundException;
import com.virginvoyages.crossreference.assembly.ReferenceSourcesAssembly;
import com.virginvoyages.crossreference.assembly.ReferenceTypesAssembly;
import com.virginvoyages.crossreference.helper.TestDataHelper;
import com.virginvoyages.model.crossreference.ReferenceSource;
import com.virginvoyages.model.crossreference.ReferenceType;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ReferenceTypesAssemblyImplIT {
	
	@Autowired
	private ReferenceTypesAssembly referenceTypesAssembly;
	
	@Autowired
	private ReferenceSourcesAssembly referenceSourcesAssembly;
	
	@Autowired
	private TestDataHelper testDataHelper;
	
	//Add
	@Test
	public void givenValidReferenceTypeAddReferenceTypeShouldCreateReferenceTypeAndReturnCreatedType() {
		
		ReferenceSource createdReferenceSource = referenceSourcesAssembly.addReferenceSource(
				testDataHelper.getReferenceSourceBusinessEntity());
		
		ReferenceType createdReferenceType = referenceTypesAssembly.addReferenceType(
				testDataHelper.getReferenceTypeBusinessEntity(createdReferenceSource));
		assertThat(createdReferenceType.referenceTypeID(),notNullValue());
				
		//Assert by find
		ReferenceType retrievedReferenceType = referenceTypesAssembly.findReferenceTypeByID(createdReferenceType.referenceTypeID());
		assertThat(retrievedReferenceType, notNullValue());
		assertThat(retrievedReferenceType.referenceTypeID(), equalTo(createdReferenceType.referenceTypeID()));
		assertThat(retrievedReferenceType.referenceType(), equalTo(createdReferenceType.referenceType()));
				
		//cleanup
		referenceTypesAssembly.deleteReferenceTypeByID(createdReferenceType.referenceTypeID());
		referenceSourcesAssembly.deleteReferenceSourceByID(createdReferenceSource.referenceSourceID());
		
	} 
	
	@Test
	public void givenEmptyReferenceTypeNameAddReferenceTypeShouldThrowDataInsertException() {
		
		ReferenceSource createdReferenceSource = referenceSourcesAssembly.addReferenceSource(
				testDataHelper.getReferenceSourceBusinessEntity());
		
		ReferenceType referenceTypeToCreate = testDataHelper.getReferenceTypeBusinessEntity(createdReferenceSource);
		referenceTypeToCreate.referenceType(StringUtils.EMPTY);
		try {
			referenceTypesAssembly.addReferenceType(referenceTypeToCreate);
		}catch(DataInsertionException ex) {
			assert(true);
			return;
		}finally {
			//cleanup
			referenceSourcesAssembly.deleteReferenceSourceByID(createdReferenceSource.referenceSourceID());
		}
		assert(false);
		
	}
	
	@Test(expected = DataInsertionException.class)
	public void givenInvalidReferenceSourceIDAddReferenceTypeShouldThrowDataInsertException() {
		referenceTypesAssembly.addReferenceType(
				testDataHelper.getReferenceTypeBusinessEntity(new ReferenceSource().referenceSourceID("dummy")));
	}
	
	@Test 
	public void givenReferenceTypeIDHasValidTypeIDAddReferenceTypeShouldIgnoreIDAndCreateTypeWithUUID() {
		
		ReferenceSource createdReferenceSource = referenceSourcesAssembly.addReferenceSource(
				testDataHelper.getReferenceSourceBusinessEntity());
		
		ReferenceType createdReferenceType = referenceTypesAssembly.addReferenceType(
				testDataHelper.getReferenceTypeBusinessEntity(createdReferenceSource));
		
		ReferenceType createdReferenceTypeWithExistingID = referenceTypesAssembly.addReferenceType(
																	testDataHelper.getReferenceTypeBusinessEntity(createdReferenceSource)
																						.referenceTypeID(createdReferenceType.referenceTypeID()));
		assertThat(createdReferenceType.referenceTypeID(), not(equalTo(createdReferenceTypeWithExistingID.referenceTypeID())));
		
		//cleanup
		referenceTypesAssembly.deleteReferenceTypeByID(createdReferenceTypeWithExistingID.referenceTypeID());
		referenceTypesAssembly.deleteReferenceTypeByID(createdReferenceType.referenceTypeID());
		referenceSourcesAssembly.deleteReferenceSourceByID(createdReferenceSource.referenceSourceID());
				
	}
	
	//Find By ID
	public void givenInvalidReferenceTypeIdFindReferenceTypeByIDShouldReturnNull() {
		assertThat(referenceTypesAssembly.findReferenceTypeByID(
				testDataHelper.getRandomAlphanumericString()), is(nullValue()));
	}
		
	@Test
	public void givenValidReferenceTypeIDGetReferenceTypeByIdShouldReturnReferenceType() throws Exception {
		
		ReferenceSource createdReferenceSource = referenceSourcesAssembly.addReferenceSource(
				testDataHelper.getReferenceSourceBusinessEntity());

		ReferenceType createdReferenceType = referenceTypesAssembly.addReferenceType(
				testDataHelper.getReferenceTypeBusinessEntity(createdReferenceSource));

		ReferenceType findReferenceType = referenceTypesAssembly.findReferenceTypeByID(createdReferenceType.referenceTypeID());
		assertThat(findReferenceType.referenceTypeID(), is(notNullValue()));
		assertThat(findReferenceType.referenceType(), equalTo(createdReferenceType.referenceType()));
		assertThat(findReferenceType.referenceType(),is(notNullValue()));
		
		//cleanup
		referenceTypesAssembly.deleteReferenceTypeByID(createdReferenceType.referenceTypeID());
		referenceSourcesAssembly.deleteReferenceSourceByID(createdReferenceSource.referenceSourceID());
	
	}
	// find by referenceType
	@Test
	public void givenValidReferenceTypeNameFindByReferenceTypeNameShouldReturnReferenceType() {
		ReferenceSource createdReferenceSource = referenceSourcesAssembly.addReferenceSource(
				testDataHelper.getReferenceSourceBusinessEntity());

		ReferenceType createdReferenceType = referenceTypesAssembly.addReferenceType(
				testDataHelper.getReferenceTypeBusinessEntity(createdReferenceSource));

		ReferenceType findReferenceType = referenceTypesAssembly.findReferenceTypeByName(createdReferenceType.referenceType());
		assertThat(findReferenceType.referenceTypeID(), is(notNullValue()));
		assertThat(findReferenceType.referenceType(), equalTo(createdReferenceType.referenceType()));
		assertThat(findReferenceType.referenceType(),is(notNullValue()));
		
		//cleanup
		referenceTypesAssembly.deleteReferenceTypeByID(createdReferenceType.referenceTypeID());
		referenceSourcesAssembly.deleteReferenceSourceByID(createdReferenceSource.referenceSourceID());
	}
	
	@Test
	public void givenInValidReferenceTypeNameFindByReferenceTypeNameShouldReturnNull() {
		assertThat(referenceTypesAssembly.findReferenceTypeByName(
				testDataHelper.getRandomAlphanumericString()), is(nullValue()));
	}
	
	//Delete
	public void givenValidReferenceTypeDeleteReferenceTypeShouldDeleteReferenceType() {
		ReferenceSource createdReferenceSource = referenceSourcesAssembly.addReferenceSource(
				testDataHelper.getReferenceSourceBusinessEntity());
		
		ReferenceType createdReferenceType = referenceTypesAssembly.addReferenceType(
				testDataHelper.getReferenceTypeBusinessEntity(createdReferenceSource));
			
		//cleanup
		referenceTypesAssembly.deleteReferenceTypeByID(createdReferenceType.referenceTypeID());
		referenceSourcesAssembly.deleteReferenceSourceByID(createdReferenceSource.referenceSourceID());
		
		assertThat(referenceTypesAssembly.findReferenceTypeByID(createdReferenceType.referenceTypeID()), is(nullValue()));
		
	}
	

	@Test(expected = DataNotFoundException.class)
	public void givenInvalidReferenceTypeIDDeleteReferenceTypeShouldThrowDataNotFoundException() {
		referenceTypesAssembly.deleteReferenceTypeByID(testDataHelper.getRandomAlphanumericString());
	}
	
	//Update
	@Test
	public void givenValidReferenceTypeUpdateReferenceTypeShouldUpdateReferenceType() {
		ReferenceSource createdReferenceSource = referenceSourcesAssembly.addReferenceSource(
								testDataHelper.getReferenceSourceBusinessEntity());
		
		ReferenceType createdRferenceType = referenceTypesAssembly.addReferenceType(
				testDataHelper.getReferenceTypeBusinessEntity(createdReferenceSource));
		
		String referenceTypeToUpdate = testDataHelper.getRandomAlphabeticString();
		
		createdRferenceType.referenceType(referenceTypeToUpdate);
		ReferenceType updatedReferenceType = referenceTypesAssembly.updateReferenceType(createdRferenceType);
		assertThat(updatedReferenceType.referenceType(), equalTo(referenceTypeToUpdate));
		
		//cleanup
		referenceTypesAssembly.deleteReferenceTypeByID(createdRferenceType.referenceTypeID());
		referenceSourcesAssembly.deleteReferenceSourceByID(createdReferenceSource.referenceSourceID());
	}
	
	@Test
	public void givenValidReferenceTypeFindTypesShouldRetunsReferenceTypes() {
		
		ReferenceSource createdReferenceSource = referenceSourcesAssembly.addReferenceSource(
				testDataHelper.getReferenceSourceBusinessEntity());
		
		ReferenceType createdReferenceType = referenceTypesAssembly.addReferenceType(
				testDataHelper.getReferenceTypeBusinessEntity(createdReferenceSource));
		
		assertThat(referenceTypesAssembly.findTypes(), hasSize(greaterThan(0)));
		
		referenceTypesAssembly.deleteReferenceTypeByID(createdReferenceType.referenceTypeID());
		referenceSourcesAssembly.deleteReferenceSourceByID(createdReferenceSource.referenceSourceID());
	}

}
