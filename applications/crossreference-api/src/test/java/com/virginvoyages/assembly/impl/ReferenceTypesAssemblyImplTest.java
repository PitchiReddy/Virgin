package com.virginvoyages.assembly.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import java.util.List;
import com.virginvoyages.crossreference.helper.MockDataHelper;
import com.virginvoyages.crossreference.types.ReferenceType;
import com.virginvoyages.data.entities.ReferenceTypeData;
import com.virginvoyages.data.repositories.ReferenceTypeRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReferenceTypesAssemblyImplTest {

	@Autowired
	private MockDataHelper mockDataHelper;
	
	@Mock
	private ReferenceTypeRepository referenceTypeRepository;

	@InjectMocks
	private ReferenceTypesAssemblyImpl referenceTypesAssemblyImpl;


	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void givenValidReferenceTypeAddReferenceTypeShouldReturnReferenceType() {
		ReferenceTypeData mockReferenceTypeData = mockDataHelper.getMockReferenceTypeDataEntity();
		when(referenceTypeRepository.save((any(ReferenceTypeData.class)))).thenReturn(mockReferenceTypeData);
		ReferenceType createdReferenceType = referenceTypesAssemblyImpl.addReferenceType(mockReferenceTypeData.convertToBusinessEntity());
		assertThat(createdReferenceType, notNullValue());
		assertThat(createdReferenceType.referenceSourceID(), notNullValue());
		assertThat(createdReferenceType.referenceType(), equalTo(mockReferenceTypeData.referenceType()));
	}

	@Test
	public void givenValidReferenceTypeFindReferenceTypeShouldReturnReferenceType() {
		ReferenceTypeData mockReferenceTypeData = mockDataHelper.getMockReferenceTypeDataEntity();
		when(referenceTypeRepository.findOne((any(String.class)))).thenReturn(mockReferenceTypeData);
		ReferenceType findReferenceType  = referenceTypesAssemblyImpl
				.findReferenceTypeByID(mockDataHelper.getValidReferenceTypeByID());
		assertThat(findReferenceType.referenceTypeID(), is(notNullValue()));
		assertThat(findReferenceType.referenceType(), equalTo(mockReferenceTypeData.referenceType()));
	}
	
	@Test
	public void givenValidReferenceTypeUpdateReferenceTypeShouldReturnUpdatedReferenceType() {
		ReferenceTypeData mockReferenceTypeData = mockDataHelper.getMockReferenceTypeDataEntity();
		ReferenceType mockUpdateReferenceType = mockReferenceTypeData.convertToBusinessEntity()
					.referenceType(mockDataHelper.getDataForUpdateReferenceType());
		when(referenceTypeRepository.save(any(ReferenceTypeData.class)))
					.thenReturn(mockUpdateReferenceType.convertToDataEntity());
		ReferenceType updatedReferenceType = referenceTypesAssemblyImpl.updateReferenceType(mockUpdateReferenceType);
		assertThat(updatedReferenceType.referenceTypeID(), is(notNullValue()));
		assertThat(updatedReferenceType.referenceType(), equalTo(mockUpdateReferenceType.referenceType()));
	}
	
	@Test
	public void givenValidReferenceTypeFindTypesShouldRetunsReferenceTypes() {
		List<ReferenceTypeData> mockReferenceTypeDataList = mockDataHelper.getMockReferenceTypeDataEntityList();
		when(referenceTypeRepository.findAll()).thenReturn(mockReferenceTypeDataList);
		List<ReferenceType> referenceTypeList = referenceTypesAssemblyImpl.findTypes();
		assertThat(referenceTypeList, hasSize(greaterThan(0)));
		
	}
		
}
