package com.virginvoyages.assembly.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsNull.notNullValue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.assembly.ReferenceSourcesAssembly;
import com.virginvoyages.assembly.ReferenceTypesAssembly;
import com.virginvoyages.crossreference.helper.TestDataHelper;
import com.virginvoyages.crossreference.sources.ReferenceSource;
import com.virginvoyages.crossreference.types.ReferenceType;
import com.virginvoyages.data.entities.ReferenceSourceData;
import com.virginvoyages.data.entities.ReferenceTypeData;

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
	public void givenValidReferenceTypeAddReferenceTypeShouldReturnReferenceType() {
		ReferenceSourceData referenceSourceData = testDataHelper.getReferenceSourceDataEntityForCreate();
		ReferenceSource createdReferenceSource = referenceSourcesAssembly.addReferenceSource(referenceSourceData.convertToBusinessEntity());
		ReferenceTypeData mockReferenceTypeData = testDataHelper.getReferenceTypeDataForCreate(createdReferenceSource);
		ReferenceType createdRferenceType = referenceTypesAssembly.addReferenceType(mockReferenceTypeData.convertToBusinessEntity());
		assertThat(createdRferenceType.referenceTypeID(), is(notNullValue()));
		assertThat(createdRferenceType.referenceType(), equalTo(mockReferenceTypeData.referenceType()));
	} 
	
	@Test
	public void givenValidReferenceTypeIDGetReferenceTypeByIdShouldReturnReferenceType() throws Exception {
		
		ReferenceSourceData referenceSourceData = testDataHelper.getReferenceSourceDataEntityForCreate();
		ReferenceSource createdReferenceSource = referenceSourcesAssembly.addReferenceSource(referenceSourceData.convertToBusinessEntity());
		ReferenceTypeData mockReferenceTypeData = testDataHelper.getReferenceTypeDataForCreate(createdReferenceSource);
		ReferenceType createdRferenceType = referenceTypesAssembly.addReferenceType(mockReferenceTypeData.convertToBusinessEntity());

		ReferenceType findReferenceType = referenceTypesAssembly.findReferenceTypeByID(createdRferenceType.referenceTypeID());
		assertThat(findReferenceType.referenceTypeID(), is(notNullValue()));
		assertThat(findReferenceType.referenceType(), equalTo(createdRferenceType.referenceType()));
		assertThat(findReferenceType.referenceType(),is(notNullValue()));
	
	}
	
	@Test
	public void givenValidReferenceTypeDeleteReferenceTypeShouldDeleteReferenceType() {
		ReferenceSourceData referenceSourceData = testDataHelper.getReferenceSourceDataEntityForCreate();
		ReferenceSource createdReferenceSource = referenceSourcesAssembly.addReferenceSource(referenceSourceData.convertToBusinessEntity());
		ReferenceTypeData mockReferenceTypeData = testDataHelper.getReferenceTypeDataForCreate(createdReferenceSource);
		ReferenceType createdRferenceType = referenceTypesAssembly.addReferenceType(mockReferenceTypeData.convertToBusinessEntity());
		referenceTypesAssembly.deleteReferenceTypeByID(createdRferenceType.referenceTypeID());
		ReferenceType findReferenceType = referenceTypesAssembly.findReferenceTypeByID(createdRferenceType.referenceTypeID());
		assertThat(findReferenceType, is(nullValue()));
	}
	
	@Test
	public void givenValidReferenceTypeUpdateReferenceTypeShouldUpdateReferenceType() {
		ReferenceSourceData referenceSourceData = testDataHelper.getReferenceSourceDataEntityForCreate();
		ReferenceSource createdReferenceSource = referenceSourcesAssembly.addReferenceSource(referenceSourceData.convertToBusinessEntity());
		ReferenceTypeData mockReferenceTypeData = testDataHelper.getReferenceTypeDataForCreate(createdReferenceSource);
		ReferenceType createdRferenceType = referenceTypesAssembly.addReferenceType(mockReferenceTypeData.convertToBusinessEntity());
		createdRferenceType.referenceType(testDataHelper.getReferenceTypeDataForUpdate());
		ReferenceType updatedReferenceType = referenceTypesAssembly.updateReferenceType(createdRferenceType);
		assertThat(updatedReferenceType.referenceType(), equalTo(testDataHelper.getReferenceTypeDataForUpdate()));
	}
	
	@Test
	public void givenValidReferenceTypeFindTypesShouldRetunsReferenceTypes() {
		List<ReferenceType> referenceTypeList =referenceTypesAssembly.findTypes();
		assertThat(referenceTypeList, hasSize(greaterThan(0)));
		for(ReferenceType referenceType: referenceTypeList) {
			referenceTypesAssembly.deleteReferenceTypeByID(referenceType.referenceTypeID());
		}
		
	}
	
}
