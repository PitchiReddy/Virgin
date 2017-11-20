package com.virginvoyages.crossreference.assembly.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.crossreference.data.entities.ReferenceTypeData;
import com.virginvoyages.crossreference.data.repositories.ReferenceTypeRepository;
import com.virginvoyages.crossreference.helper.TestDataHelper;
import com.virginvoyages.exceptions.DataAccessException;
import com.virginvoyages.exceptions.DataInsertionException;
import com.virginvoyages.exceptions.DataNotFoundException;
import com.virginvoyages.exceptions.DataUpdationException;
import com.virginvoyages.exceptions.UnknownException;
import com.virginvoyages.model.crossreference.ReferenceType;

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

	// Add
	@Test
	public void givenRepositoryReturnsSavedEntityAddReferenceTypeShouldReturnSavedEntity() {
		ReferenceTypeData mockReferenceTypeData = testDataHelper.getReferenceTypeDataEntity();
		when(referenceTypeRepository.save((any(ReferenceTypeData.class)))).thenReturn(mockReferenceTypeData);
		ReferenceType createdReferenceType = referenceTypesAssemblyImpl
				.addReferenceType(mockReferenceTypeData.convertToBusinessEntity());
		assertThat(createdReferenceType, notNullValue());
		assertThat(createdReferenceType.referenceSourceID(), notNullValue());
		assertThat(createdReferenceType.referenceType(), equalTo(mockReferenceTypeData.referenceType()));
		assertThat(createdReferenceType.referenceTypeID(), equalTo(mockReferenceTypeData.referenceTypeID()));
	}

	@Test
	public void givenRepositoryReturnsNullAddReferenceTypeShouldReturnNull() {
		when(referenceTypeRepository.save((any(ReferenceTypeData.class)))).thenReturn(null);
		assertThat(referenceTypesAssemblyImpl.addReferenceType(testDataHelper.getReferenceTypeBusinessEntity()),
				nullValue());
	}

	@Test
	public void givenRepositoryReturnsReferenceTypeDataWithBlankReferenceTypeIDAddReferenceTypeShouldReturnNull() {
		when(referenceTypeRepository.save((any(ReferenceTypeData.class))))
				.thenReturn(new ReferenceTypeData().referenceTypeID(null));
		assertThat(referenceTypesAssemblyImpl.addReferenceType(testDataHelper.getReferenceTypeBusinessEntity()),
				nullValue());
	}

	@Test(expected = DataInsertionException.class)
	public void givenRepositoryThrowsDataIntegrityViolationExceptionAddReferenceTypeShouldThrowDataInsertException() {
		when(referenceTypeRepository.save(any(ReferenceTypeData.class)))
				.thenThrow(new DataIntegrityViolationException("test"));
		referenceTypesAssemblyImpl.addReferenceType(testDataHelper.getReferenceTypeBusinessEntity());
	}

	@Test(expected = UnknownException.class)
	public void givenRepositoryThrowsAnyExceptionAddReferenceSourcesShouldThrowUnknownException() {
		when(referenceTypeRepository.save(new ReferenceTypeData())).thenThrow(new RuntimeException());
		referenceTypesAssemblyImpl.addReferenceType(testDataHelper.getReferenceTypeBusinessEntity());
	}

	// Find By ID
	@Test
	public void givenRepositoryReturnsValidReferenceTypeDatafindReferenceTypeByIDShouldReturnReferenceType() {
		ReferenceTypeData mockReferenceTypeData = testDataHelper.getReferenceTypeDataEntity();
		when(referenceTypeRepository.findOne((any(String.class)))).thenReturn(mockReferenceTypeData);
		ReferenceType referenceType = referenceTypesAssemblyImpl
				.findReferenceTypeByID(testDataHelper.getRandomAlphabeticString());
		assertThat(referenceType.referenceTypeID(), is(notNullValue()));
		assertThat(referenceType.referenceType(), equalTo(mockReferenceTypeData.referenceType()));
	}

	public void givenRepositoryReturnsNullfindReferenceTypeByIDShouldReturnNull() {
		when(referenceTypeRepository.findOne((any(String.class)))).thenReturn(null);
		assertThat(referenceTypesAssemblyImpl.findReferenceTypeByID(testDataHelper.getRandomAlphabeticString()),
				is(nullValue()));

	}

	@Test(expected = UnknownException.class)
	public void givenRepositoryThrowsAnyExceptionFindReferenceByIDShouldThrowUnknownException() {
		when(referenceTypeRepository.findOne((any(String.class)))).thenThrow(new RuntimeException());
		referenceTypesAssemblyImpl.findReferenceTypeByID((testDataHelper.getRandomAlphabeticString()));
	}

	// Delete
	@Test
	public void givenDeleteOnRepositoryDoesNotThrowAnyExceptionDeleteReferenceTypeByIDShouldReturnTrue() {
		doNothing().when(referenceTypeRepository).delete(testDataHelper.getRandomAlphabeticString());
		assert (referenceTypesAssemblyImpl.deleteReferenceTypeByID(testDataHelper.getRandomAlphanumericString()));

	}

	@Test(expected = DataNotFoundException.class)
	public void givenDeleteOnRepositoryThrowsEmptyResultDataAccessExceptionDeleteReferenceTypeByIDShouldThrowDataNotFoundException() {
		doThrow(new EmptyResultDataAccessException(1)).when(referenceTypeRepository).delete(any(String.class));
		referenceTypesAssemblyImpl.deleteReferenceTypeByID(testDataHelper.getRandomAlphanumericString());

	}

	@Test(expected = DataAccessException.class)
	public void givenDeleteOnRepositoryThrowsDataIntegrityViolationExceptionDeleteReferenceTypeByIDShouldThrowDataAccessException() {
		doThrow(new DataIntegrityViolationException("test")).when(referenceTypeRepository).delete(any(String.class));
		referenceTypesAssemblyImpl.deleteReferenceTypeByID(testDataHelper.getRandomAlphanumericString());

	}

	@Test(expected = UnknownException.class)
	public void givenDeleteOnRepositoryThrowsAnyExceptionDeleteReferenceTypeByIDShouldThrowUnknownException() {
		doThrow(new RuntimeException()).when(referenceTypeRepository).delete(any(String.class));
		referenceTypesAssemblyImpl.deleteReferenceTypeByID(testDataHelper.getRandomAlphanumericString());
	}

	// Update
	
	@Test(expected = DataUpdationException.class)
	public void givenRepositoryReturnsFalseForExistsUpdateReferenceTypeShouldThrowDataUpdateException() {
		when(referenceTypeRepository.exists(testDataHelper.getRandomAlphabeticString())).thenReturn(false);
		referenceTypesAssemblyImpl.updateReferenceType(testDataHelper.getReferenceTypeBusinessEntity());
	}

	@Test(expected = DataUpdationException.class)
	public void givenRepositoryThrowsDataIntegrityViolationExceptionUpdateReferenceTypeShouldThrowDataUpdateException() {
		when(referenceTypeRepository.exists((any(String.class)))).thenReturn(true);
		when(referenceTypeRepository.save(testDataHelper.getReferenceTypeDataEntity()))
				.thenThrow(new DataIntegrityViolationException("test"));
		referenceTypesAssemblyImpl.updateReferenceType(testDataHelper.getReferenceTypeBusinessEntity());
	}

	@Test(expected=UnknownException.class)
    public void givenRepositoryThrowsAnyOtherExceptionUpdateReferenceTypesShouldThrowUnknownException() {
		when(referenceTypeRepository.exists((any(String.class)))).thenReturn(true);
		when(referenceTypeRepository.save(testDataHelper.getReferenceTypeDataEntity()))
				.thenThrow(new RuntimeException());
		referenceTypesAssemblyImpl.updateReferenceType(testDataHelper.getReferenceTypeBusinessEntity());
	}
	@Test
	public void givenRepositoryReturnsTrueForExistsAndRepositoryReturnsUpdatedTypeUpdateReferenceTypeShouldReturnUpdatedEntity() {
		ReferenceTypeData mockReferenceTypeData = testDataHelper.getReferenceTypeDataEntity();
		when(referenceTypeRepository.exists((any(String.class)))).thenReturn(true);
		when(referenceTypeRepository.save(any(ReferenceTypeData.class))).thenReturn(mockReferenceTypeData);
		ReferenceType createdReferenceType = referenceTypesAssemblyImpl
				.updateReferenceType(mockReferenceTypeData.convertToBusinessEntity());
		assertThat(createdReferenceType, notNullValue());
		assertThat(createdReferenceType.referenceTypeID(), notNullValue());
		assertThat(createdReferenceType.referenceType(), equalTo(mockReferenceTypeData.referenceType()));
	}
	
	@Test
	public void givenRepositoryReturnsTrueForExistsAndRepositoryReturnsNullUpdateReferenceTypeShouldReturnNull() {
		when(referenceTypeRepository.exists((any(String.class)))).thenReturn(true);
		when(referenceTypeRepository.save(any(ReferenceTypeData.class))).thenReturn(null);
		assertThat(referenceTypesAssemblyImpl
				.updateReferenceType(testDataHelper.getEmptyReferenceTypeBusinessEntity()), nullValue());
	}
	
	@Test
	public void givenRepositoryReturnsTrueForExistsAndRepositoryReturnsEntityWithNullIDUpdateReferenceTypeShouldReturnNull() {
		//ReferenceTypeData mockReferenceTypeData = testDataHelper.getReferenceTypeDataEntity();
		when(referenceTypeRepository.exists((any(String.class)))).thenReturn(true);
		when(referenceTypeRepository.save(any(ReferenceTypeData.class))).thenReturn(testDataHelper.getReferenceTypeDataEntity().referenceTypeID(null));
		assertThat(referenceTypesAssemblyImpl
				.updateReferenceType(testDataHelper.getEmptyReferenceTypeBusinessEntity()), nullValue());
	}
	
	//Find All

	@Test
	public void givenRepositoryReturnsListOfReferenceTypesDataFindTypesShouldReturnCorrespondingReferenceTypes() {
		Page<ReferenceTypeData> pagedReferenceSourceData = testDataHelper.getPagedReferenceTypeDataEntity();
		List<ReferenceTypeData> mockReferenceTypeDataList = new ArrayList<ReferenceTypeData>();
		mockReferenceTypeDataList.add(testDataHelper.getReferenceTypeDataEntity());
		when(referenceTypeRepository.findAll(any(Pageable.class))).thenReturn(pagedReferenceSourceData);
		List<ReferenceType> referenceSourceList = referenceTypesAssemblyImpl.findTypes(new PageRequest(0, 10));
 		assertThat(referenceSourceList, hasSize(equalTo(mockReferenceTypeDataList.size())));


	}

	/*
	 * @Test(expected=DataNotFoundException.class) public void
	 * givenRepositoryReturnsValidReferenceTypeDataDeleteReferenceTypeByIDShouldReturnEmptyReferenceType
	 * () { Mockito.spy(ReferenceTypeData.class);
	 * referenceTypesAssemblyImpl.deleteReferenceTypeByID(testDataHelper.
	 * getRandomAlphabeticString());
	 * assertThat(referenceTypesAssemblyImpl.findReferenceTypeByID(
	 * testDataHelper.getRandomAlphanumericString()), is(nullValue()));
	 * 
	 * 
	 * }
	 */
}
