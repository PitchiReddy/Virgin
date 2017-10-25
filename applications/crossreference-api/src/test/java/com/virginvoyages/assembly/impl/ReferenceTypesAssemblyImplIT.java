package com.virginvoyages.assembly.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.virginvoyages.assembly.ReferenceSourcesAssembly;
import com.virginvoyages.assembly.ReferenceTypesAssembly;
import com.virginvoyages.crossreference.exceptions.DataNotFoundException;
import com.virginvoyages.crossreference.exceptions.MandatoryFieldsMissingException;
import com.virginvoyages.crossreference.helper.TestDataHelper;
import com.virginvoyages.crossreference.sources.ReferenceSource;
import com.virginvoyages.crossreference.types.ReferenceType;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ReferenceTypesAssemblyImplIT {
	
	

	@Autowired
	private ReferenceTypesAssembly referenceTypesAssembly;
	
	@Autowired
	private ReferenceSourcesAssembly referenceSourcesAssembly;
	
	@Autowired
	private TestDataHelper testDataHelper;
	
		
	@Test
	public void givenValidReferenceTypeAddReferenceTypeShouldCreateReferenceTypeAndReturnCreatedType() {
		
		ReferenceSource referenceSourceToCreate = testDataHelper.getReferenceSourceBusinessEntity();
		ReferenceSource createdReferenceSource = referenceSourcesAssembly.addReferenceSource(referenceSourceToCreate);
		
		ReferenceType referenceTypeToCreate = testDataHelper.getReferenceTypeBusinessEntity(createdReferenceSource);
		ReferenceType createdReferenceType = referenceTypesAssembly.addReferenceType(referenceTypeToCreate);
		assertThat(createdReferenceType.referenceTypeID(),notNullValue());
		assertThat(createdReferenceType.referenceType(), equalTo(referenceTypeToCreate.referenceType()));
		
		//Assert by find
		ReferenceType retrievedReferenceType = referenceTypesAssembly.findReferenceTypeByID(createdReferenceType.referenceTypeID());
		assertThat(retrievedReferenceType, notNullValue());
		assertThat(retrievedReferenceType.referenceTypeID(), equalTo(createdReferenceType.referenceTypeID()));
		assertThat(retrievedReferenceType.referenceType(), equalTo(createdReferenceType.referenceType()));
				
		//cleanup
		referenceTypesAssembly.deleteReferenceTypeByID(createdReferenceType.referenceTypeID());
		referenceSourcesAssembly.deleteReferenceSourceByID(createdReferenceSource.referenceSourceID());
		
	} 
	
	@Test(expected= DataNotFoundException.class)
	public void givenInvalidReferenceSourceIdFindReferenceTypeShouldThrowInvalidReferenceSourceException() {
		ReferenceType retrievedReferenceType = referenceTypesAssembly.findReferenceTypeByID(testDataHelper.getRandomAlphanumericString());
		assertThat(retrievedReferenceType, is(nullValue()));
	}
	
	
	@Test
	public void givenValidReferenceTypeIDGetReferenceTypeByIdShouldReturnReferenceType() throws Exception {
		
		ReferenceSource referenceSource = testDataHelper.getReferenceSourceBusinessEntity();
		ReferenceSource createdReferenceSource = referenceSourcesAssembly.addReferenceSource(referenceSource);
		
		ReferenceType referenceType = testDataHelper.getReferenceTypeBusinessEntity(createdReferenceSource);
		ReferenceType createdReferenceType = referenceTypesAssembly.addReferenceType(referenceType);

		ReferenceType findReferenceType = referenceTypesAssembly.findReferenceTypeByID(createdReferenceType.referenceTypeID());
		assertThat(findReferenceType.referenceTypeID(), is(notNullValue()));
		assertThat(findReferenceType.referenceType(), equalTo(createdReferenceType.referenceType()));
		assertThat(findReferenceType.referenceType(),is(notNullValue()));
		
		//cleanup
		referenceTypesAssembly.deleteReferenceTypeByID(createdReferenceType.referenceTypeID());
		referenceSourcesAssembly.deleteReferenceSourceByID(createdReferenceSource.referenceSourceID());
	
	}
	
	@Test(expected = DataNotFoundException.class)
	public void givenValidReferenceTypeDeleteReferenceTypeShouldDeleteReferenceType() {
		ReferenceSource referenceSource = testDataHelper.getReferenceSourceBusinessEntity();
		ReferenceSource createdReferenceSource = referenceSourcesAssembly.addReferenceSource(referenceSource);
		
		ReferenceType referenceType = testDataHelper.getReferenceTypeBusinessEntity(createdReferenceSource);
		ReferenceType createdReferenceType = referenceTypesAssembly.addReferenceType(referenceType);
		
		
		//cleanup
		referenceTypesAssembly.deleteReferenceTypeByID(createdReferenceType.referenceTypeID());
		referenceSourcesAssembly.deleteReferenceSourceByID(createdReferenceSource.referenceSourceID());
		
		ReferenceType findReferenceType = referenceTypesAssembly.findReferenceTypeByID(createdReferenceType.referenceTypeID());
		assertThat(findReferenceType, is(nullValue()));
		
	}
	

	@Test(expected = DataNotFoundException.class)
	public void givenInvalidReferenceTypeIDDeleteReferenceTypeShouldThrowEmptyDataNotFoundException() {
		referenceTypesAssembly.deleteReferenceTypeByID(testDataHelper.getRandomAlphanumericString());
	}
	
	@Test
	public void givenValidReferenceTypeUpdateReferenceTypeShouldUpdateReferenceType() {
		ReferenceSource referenceSource = testDataHelper.getReferenceSourceBusinessEntity();
		ReferenceSource createdReferenceSource = referenceSourcesAssembly.addReferenceSource(referenceSource);
		
		ReferenceType referenceType = testDataHelper.getReferenceTypeBusinessEntity(createdReferenceSource);
		ReferenceType createdRferenceType = referenceTypesAssembly.addReferenceType(referenceType);
		
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
		
		ReferenceSource referenceSourceToCreate = testDataHelper.getReferenceSourceBusinessEntity();
		ReferenceSource createdReferenceSource = referenceSourcesAssembly.addReferenceSource(referenceSourceToCreate);
		
		ReferenceType referenceType = testDataHelper.getReferenceTypeBusinessEntity(createdReferenceSource);
		ReferenceType createdReferenceType = referenceTypesAssembly.addReferenceType(referenceType);
		
		List<ReferenceType> referenceTypeList =referenceTypesAssembly.findTypes();
		assertThat(referenceTypeList, hasSize(greaterThan(0)));
		
		referenceTypesAssembly.deleteReferenceTypeByID(createdReferenceType.referenceTypeID());
		referenceSourcesAssembly.deleteReferenceSourceByID(createdReferenceSource.referenceSourceID());
	}
	
	@Test(expected= MandatoryFieldsMissingException.class)
	public void givenEmptyStringAsReferenceTypeAddReferenceTypeShouldThrowMandatoryFieldsMissingException() {
		
		ReferenceSource referenceSourceToCreate = testDataHelper.getEmptyReferenceSourceBusinessEntity();
		ReferenceSource createdReferenceSource = referenceSourcesAssembly.addReferenceSource(referenceSourceToCreate);
		ReferenceType referenceType = testDataHelper.getEmptyReferenceTypeBusinessEntity(createdReferenceSource);
		ReferenceType createdReferenceType = referenceTypesAssembly.addReferenceType(referenceType);
		assertThat(createdReferenceType, is(nullValue()));
		
	}
}
