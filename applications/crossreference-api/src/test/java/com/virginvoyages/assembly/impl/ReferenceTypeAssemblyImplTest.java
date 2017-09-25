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
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import com.virginvoyages.crossreference.helper.MockDataHelper;
import com.virginvoyages.crossreference.types.ReferenceType;
import com.virginvoyages.dao.impl.ReferenceTypeDAOImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReferenceTypeAssemblyImplTest {

	@Autowired
	private MockDataHelper mockDataHelper;

	@InjectMocks
	private ReferenceTypesAssemblyImpl referenceTypesAssemblyImpl;

	@Mock
	private ReferenceTypeDAOImpl referenceTypeDAOImpl;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void givenValidReferenceTypeAddReferenceTypeShouldReturnReferenceType() {
		ReferenceType referenceType = mockDataHelper.getDataForCreateReferenceType();
		referenceTypesAssemblyImpl.addReferenceType(referenceType);
		when(referenceTypeDAOImpl.findReferenceTypeByID(any(String.class)))
				.thenReturn(mockDataHelper.getReferenceTypeByID());
		ReferenceType getReferenceType = referenceTypesAssemblyImpl
				.findReferenceTypeByID(mockDataHelper.getValidReferenceTypeByID());
		assertThat(getReferenceType.referenceTypeID(), is(notNullValue()));
		assertThat(getReferenceType.referenceType(), equalTo("Reservation"));
	}

	@Test
	public void givenValidReferenceTypeFindReferenceTypeShouldReturnReferenceType() {
		when(referenceTypeDAOImpl.findReferenceTypeByID(any(String.class)))
				.thenReturn(mockDataHelper.getReferenceTypeByID());
		ReferenceType referenceType = referenceTypesAssemblyImpl
				.findReferenceTypeByID(mockDataHelper.getValidReferenceTypeByID());
		assertThat(referenceType.referenceTypeID(), is(notNullValue()));
		assertThat(referenceType.referenceType(), equalTo("Reservation"));
	}
	
	@Test
	public void givenValidReferenceTypeUpdateReferenceTypeShouldReturnUpdatedReferenceType() {
		ReferenceType referenceType = mockDataHelper.getDataForCreateReferenceType();
		referenceType.referenceName("Updated_referenceName");
		referenceTypesAssemblyImpl.updateReferenceType(referenceType.referenceTypeID(), referenceType);
		assertThat(referenceType.referenceTypeID(), is(notNullValue()));
		assertThat(referenceType.referenceName(), equalTo("Updated_referenceName"));
	}
}
