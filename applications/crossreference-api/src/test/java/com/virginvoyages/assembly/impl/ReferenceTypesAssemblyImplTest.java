package com.virginvoyages.assembly.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.crossreference.data.entities.ReferenceTypeData;
import com.virginvoyages.crossreference.exceptions.DataInsertionException;
import com.virginvoyages.crossreference.exceptions.DataNotFoundException;
import com.virginvoyages.crossreference.exceptions.UnknownException;
import com.virginvoyages.crossreference.helper.TestDataHelper;
import com.virginvoyages.model.crossreference.ReferenceType;
import com.virginvoyages.data.repositories.ReferenceTypeRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReferenceTypesAssemblyImplTest {

	@Mock
	private ReferenceTypeRepository referenceTypeRepository;

	@InjectMocks
	private ReferenceTypesAssemblyImpl referenceTypesAssemblyImpl;

	@Autowired
	private TestDataHelper testDataHelper;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	//Add
	@Test
	public void givenRepositoryReturnsSavedEntityAddReferenceTypeShouldReturnSavedEntity() {
		ReferenceTypeData mockReferenceTypeData = testDataHelper.getReferenceTypeDataEntity();
		when(referenceTypeRepository.save((any(ReferenceTypeData.class)))).thenReturn(mockReferenceTypeData);
		ReferenceType createdReferenceType = referenceTypesAssemblyImpl.addReferenceType(mockReferenceTypeData.convertToBusinessEntity());
		assertThat(createdReferenceType, notNullValue());
		assertThat(createdReferenceType.referenceSourceID(), notNullValue());
		assertThat(createdReferenceType.referenceType(), equalTo(mockReferenceTypeData.referenceType()));
		assertThat(createdReferenceType.referenceTypeID(), equalTo(mockReferenceTypeData.referenceTypeID()));
	}
	
	@Test
	public void givenRepositoryReturnsNullAddReferenceTypeShouldReturnNull() {
		when(referenceTypeRepository.save((any(ReferenceTypeData.class)))).thenReturn(null);
		assertThat(referenceTypesAssemblyImpl.addReferenceType(testDataHelper.getReferenceTypeBusinessEntity()), nullValue());
	}
	
	@Test
	public void givenRepositoryReturnsReferenceTypeDataWithBlankReferenceTypeIDAddReferenceTypeShouldReturnNull() {
		when(referenceTypeRepository.save((any(ReferenceTypeData.class)))).thenReturn(new ReferenceTypeData().referenceTypeID(null));
		assertThat(referenceTypesAssemblyImpl.addReferenceType(
				testDataHelper.getReferenceTypeBusinessEntity()), nullValue());
	}
	
	@Test(expected = DataInsertionException.class)
	public void givenRepositoryThrowsDataIntegrityViolationExceptionAddReferenceTypeShouldThrowDataInsertException() {
		when(referenceTypeRepository.save(any(ReferenceTypeData.class))).thenThrow(new DataIntegrityViolationException("test"));
		referenceTypesAssemblyImpl.addReferenceType(testDataHelper.getReferenceTypeBusinessEntity());
	}
	
	@Test(expected = UnknownException.class)
	public void givenRepositoryThrowsAnyExceptionAddReferenceSourcesShouldThrowDataInsertException() {
		when(referenceTypeRepository.save(new ReferenceTypeData())).thenThrow(new RuntimeException());
		referenceTypesAssemblyImpl.addReferenceType(testDataHelper.getReferenceTypeBusinessEntity());
	}
	/*@Test
	public void givenRepositorySavesReferenceTypeDataUpdateReferenceTypeShouldReturnSavedEntity() {
		ReferenceTypeData mockReferenceTypeData = testDataHelper.getReferenceTypeDataEntity();
		when(referenceTypeRepository.findOne((any(String.class)))).thenReturn(mockReferenceTypeData);
		when(referenceTypeRepository.save((any(ReferenceTypeData.class)))).thenReturn(mockReferenceTypeData);
		ReferenceType createdReferenceType = referenceTypesAssemblyImpl.updateReferenceType(mockReferenceTypeData.convertToBusinessEntity());
		assertThat(createdReferenceType, notNullValue());
		assertThat(createdReferenceType.referenceSourceID(), notNullValue());
		assertThat(createdReferenceType.referenceType(), equalTo(mockReferenceTypeData.referenceType()));
	}*/
	@Test
	public void givenRepositoryReturnsUpdatedReferenceTypeDataUpdateReferenceTypeShouldReturnUpdatedEntity() {
		ReferenceTypeData mockReferenceTypeData = testDataHelper.getReferenceTypeDataEntity();
		when(referenceTypeRepository.findOne((any(String.class)))).thenReturn(mockReferenceTypeData);
		when(referenceTypeRepository.save((any(ReferenceTypeData.class)))).thenReturn(mockReferenceTypeData);
		ReferenceType updatedReferenceType = referenceTypesAssemblyImpl.updateReferenceType(mockReferenceTypeData.convertToBusinessEntity());
		assertThat(updatedReferenceType, notNullValue());
		assertThat(updatedReferenceType.referenceTypeID(), equalTo(mockReferenceTypeData.referenceTypeID()));
	}
	
	/*@Test //TODO XREF TESTS
	public void givenFineOneReturnsNoReferenceTypeDataUpdateReferenceTypeShouldThrowDataUpdationException() {
		
	}*/

	@Test
	public void givenRepositoryReturnsValidReferenceTypeDatafindReferenceTypeByIDShouldReturnReferenceType() {
		ReferenceTypeData mockReferenceTypeData = testDataHelper.getReferenceTypeDataEntity();
		when(referenceTypeRepository.findOne((any(String.class)))).thenReturn(mockReferenceTypeData);
		ReferenceType findReferenceType  = referenceTypesAssemblyImpl
				.findReferenceTypeByID(testDataHelper.getRandomAlphabeticString());
		assertThat(findReferenceType.referenceTypeID(), is(notNullValue()));
		assertThat(findReferenceType.referenceType(), equalTo(mockReferenceTypeData.referenceType()));
	}
	
	
	/*@Test(expected=DataNotFoundException.class)
	public void givenRepositoryReturnsNoReferenceTypeDatafindReferenceTypeByIDShouldThrowDataNotFoundException() {
		ReferenceType findReferenceType  = referenceTypesAssemblyImpl
				.findReferenceTypeByID(testDataHelper.getRandomAlphabeticString());
		assertThat(findReferenceType.referenceTypeID(), is(nullValue()));
	}*/
	
	@Test
	public void givenRepositoryReturnsListOfReferenceTypesDataFindTypesShouldReturnCorrespondingReferenceTypes() {
		List<ReferenceTypeData> mockReferenceTypeDataList = new ArrayList<ReferenceTypeData>();
		mockReferenceTypeDataList.add(testDataHelper.getReferenceTypeDataEntity());
		mockReferenceTypeDataList.add(testDataHelper.getReferenceTypeDataEntity());
		
		when(referenceTypeRepository.findAll()).thenReturn(mockReferenceTypeDataList);
		assertThat(referenceTypesAssemblyImpl.findTypes(), hasSize(equalTo(mockReferenceTypeDataList.size())));
		
	}
	
	@Test(expected=DataNotFoundException.class)
	public void givenRepositoryReturnsValidReferenceTypeDataDeleteReferenceTypeByIDShouldReturnEmptyReferenceType() {
		Mockito.spy(ReferenceTypeData.class);
		referenceTypesAssemblyImpl.deleteReferenceTypeByID(testDataHelper.getRandomAlphabeticString());
		assertThat(referenceTypesAssemblyImpl.findReferenceTypeByID(
				testDataHelper.getRandomAlphanumericString()), is(nullValue()));
	
		
	  }
}
