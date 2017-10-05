package com.virginvoyages.assembly.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsNull.notNullValue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.assembly.ReferenceTypesAssembly;
import com.virginvoyages.crossreference.helper.TestDataHelper;
import com.virginvoyages.crossreference.types.ReferenceType;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReferenceTypesAssemblyImplIT {

	@Autowired
	private ReferenceTypesAssembly referenceTypesAssembly;
	
	@Autowired
	private TestDataHelper testDataHelper;
	
	@Test
	public void givenValidReferenceTypeAddReferenceTypeShouldReturnReferenceType() {
		ReferenceType referenceType = testDataHelper.getDataForCreateReferenceType();
		referenceTypesAssembly.addReferenceType(referenceType);
		assertThat(referenceType.referenceTypeID(), is(notNullValue()));
		assertThat(referenceType.referenceType(), equalTo("Reservation"));
		//assertThat(referenceType.referenceName(), equalTo("Activity"));
	} 
	
	@Test
	public void givenValidReferenceTypeIDGetReferenceTypeByIdShouldReturnReferenceType() throws Exception {
		
		ReferenceType createReference = testDataHelper.getDataForCreateReferenceType();
		referenceTypesAssembly.addReferenceType(createReference);
		ReferenceType referenceType = referenceTypesAssembly.findReferenceTypeByID(createReference.referenceTypeID());
		assertThat(referenceType.referenceTypeID(), is(notNullValue()));
		assertThat(referenceType.referenceType(), equalTo("Reservation"));
		assertThat(referenceType.referenceType(),is(notNullValue()));
	
	}
	
	@Test
	public void givenValidReferenceTypeDeleteReferenceTypeShouldDeleteReferenceType() {
		ReferenceType referenceType = testDataHelper.getDataForCreateReferenceType();
		referenceTypesAssembly.deleteReferenceTypeByID(referenceType.referenceTypeID());
	//	assertThat(referenceType.referenceTypeID(), is(nullValue()));
	}
	
	@Test
	public void givenValidReferenceTypeUpdateReferenceTypeShouldUpdateReferenceType() {
		ReferenceType referenceType = testDataHelper.getDataForCreateReferenceType();
		//referenceType.referenceName("siva_shankar");
		referenceTypesAssembly.updateReferenceType(referenceType.referenceTypeID(), referenceType);
		//assertThat(referenceType.referenceName(), equalTo("siva_shankar"));
	}
	
	@Test
	public void givenValidReferenceTypeFindTypesShouldRetunsReferenceTypes() {
		testDataHelper.getDataForCreateReferenceType();
		List<ReferenceType> referenceTypeList =referenceTypesAssembly.findTypes();
		assertThat(referenceTypeList, hasSize(4));
		for(ReferenceType referenceType: referenceTypeList) {
			//assertThat(referenceType.referenceName(), equalTo("Activity"));
			referenceTypesAssembly.deleteReferenceTypeByID(referenceType.referenceTypeID());
		}
		
	}
	
}
