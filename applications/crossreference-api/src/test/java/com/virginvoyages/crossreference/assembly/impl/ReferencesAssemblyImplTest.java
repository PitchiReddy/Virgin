package com.virginvoyages.crossreference.assembly.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Matchers.any;
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
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.crossreference.data.entities.ReferenceData;
import com.virginvoyages.crossreference.data.repositories.ReferenceRepository;
import com.virginvoyages.crossreference.data.repositories.ReferenceTypeRepository;
import com.virginvoyages.crossreference.helper.CrossReferenceEntityMapper;
import com.virginvoyages.crossreference.helper.TestDataHelper;
import com.virginvoyages.crossreference.model.Reference;
import com.virginvoyages.exception.DataAccessException;
import com.virginvoyages.exception.DataNotFoundException;
import com.virginvoyages.exception.DataUpdationException;
import com.virginvoyages.exception.UnknownException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReferencesAssemblyImplTest {
	

	@Mock
	private ReferenceRepository referenceRepository;
	
	@Mock
	private ReferenceTypeRepository referenceTypeRepository;
	
	@InjectMocks
	private ReferencesAssemblyImpl referencesAssemblyImpl;
	
	@Autowired
	private TestDataHelper testDataHelper;
	
	@Spy
	private CrossReferenceEntityMapper entityMapper;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void givenRepositorySavesReferenceTypeDataAddReferenceTypeShouldReturnSavedEntity() {
		ReferenceData mockReferenceData = testDataHelper.getReferenceDataEntity();
		when(referenceRepository.save((any(ReferenceData.class)))).thenReturn(mockReferenceData);
		Reference createdReference = referencesAssemblyImpl.addReference(entityMapper.convertToReferenceBusinessEntity(mockReferenceData));
		assertThat(createdReference, notNullValue());
		assertThat(createdReference.referenceID(), notNullValue());
		assertThat(createdReference.masterID(), equalTo(mockReferenceData.masterID()));
	}
	
	/*@Test
	public void givenRepositorySavesReferenceDataUpdateReferenceShouldReturnSavedEntity() {
		ReferenceData mockReferenceData = testDataHelper.getReferenceDataEntity();
		when(referenceRepository.findOne((any(String.class)))).thenReturn(mockReferenceData);
		when(referenceRepository.save(any(ReferenceData.class))).thenReturn(mockReferenceData);
		Reference createdReference = referencesAssemblyImpl.updateReference(mockReferenceData.convertToBusinessEntity());
		assertThat(createdReference, notNullValue());
		assertThat(createdReference.referenceID(), notNullValue());
		assertThat(createdReference.masterID(), equalTo(mockReferenceData.masterID()));
	}*/
	
	@Test(expected = DataNotFoundException.class)
	public void givenRepositoryThrowsEmptyResultDataAccessExceptionDeleteReferenceByIDShouldThrowDataNotFoundException() {
		doThrow(new EmptyResultDataAccessException(0)).when(referenceRepository).delete(any(String.class));
		referencesAssemblyImpl.deleteReferenceByID(testDataHelper.getRandomAlphabeticString());
	}
	
	/*@Test(expected = DataNotFoundException.class)
	public void givenRepositoryvalidReferenceIDInDeleteReferenceByIDShouldReturnEmptyReference() {
		Mockito.spy(ReferenceData.class);
		ReferenceData mockReferenceData = testDataHelper.getReferenceDataEntity();
		doNothing().when(referenceRepository).delete(mockReferenceData.referenceID());
		referencesAssemblyImpl.deleteReferenceByID(mockReferenceData.referenceID());
		doThrow(new DataNotFoundException()).when(referenceRepository).findOne(mockReferenceData.referenceID());
		Reference findReference = referencesAssemblyImpl.findReferenceByID(mockReferenceData.referenceID());
		assertThat(findReference, is(nullValue()));
		
	}*/
	
	@Test(expected = DataUpdationException.class)
	public void givenRepositorySavesReferenceDataWithInvalidReferenceIDInUpdateReferenceItShouldThrowDataUpdationException() {
		ReferenceData mockReferenceData = testDataHelper.getReferenceDataEntity();
		when(referenceRepository.save(any(ReferenceData.class))).thenReturn(mockReferenceData);
		Reference createdReference = referencesAssemblyImpl.updateReference(entityMapper.convertToReferenceBusinessEntity(mockReferenceData));
		assertThat(createdReference, notNullValue());
		assertThat(createdReference.referenceID(), notNullValue());
		assertThat(createdReference.masterID(), equalTo(mockReferenceData.masterID()));
	}
	
	@Test
	public void givenRepositoryReturnsValidReferenceDatafindReferenceByIDShouldReturnReference() {
		ReferenceData mockReferenceData = testDataHelper.getReferenceDataEntity();
		when(referenceRepository.findOne((any(String.class)))).thenReturn(mockReferenceData);
		Reference findReference = referencesAssemblyImpl.findReferenceByID(testDataHelper.getRandomAlphabeticString());
		assertThat(findReference.referenceID(), is(notNullValue()));
		assertThat(findReference.masterID(), equalTo(mockReferenceData.masterID()));
		assertThat(findReference.nativeSourceIDValue(), equalTo(mockReferenceData.nativeSourceIDValue()));
	}

	@Test
	public void givenRepositoryReturnsNoReferenceDatafindReferenceByIDShouldReturnNull() {
		when(referenceRepository.findOne((any(String.class)))).thenReturn(null);
		assertThat(referencesAssemblyImpl.findReferenceByID(testDataHelper.getRandomAlphabeticString()), is(nullValue()));
	}

	/*@Test
	public void givenRepositoryReturnsListOfReferenceDataFindReferencesShouldReturnCorrespondingReferences() {

		List<ReferenceData> mockReferenceList = new ArrayList<ReferenceData>();
		mockReferenceList.add(testDataHelper.getReferenceDataEntity());
		mockReferenceList.add(testDataHelper.getReferenceDataEntity());

		when(referenceRepository.findAll()).thenReturn(mockReferenceList);
		List<Reference> referenceList = referencesAssemblyImpl.findReferences();
		assertThat(referenceList, hasSize(equalTo(mockReferenceList.size())));
	}
	 */
	@Test
	public void givenRepositoryReturnsTrueForExistsAndRepositoryUpdatessuccessfullyUpdateReferenceShouldReturnUpdatedEntity() {
		ReferenceData mockReferenceData = testDataHelper.getReferenceDataEntity();
		when(referenceRepository.exists((any(String.class)))).thenReturn(true);
		when(referenceRepository.save(any(ReferenceData.class))).thenReturn(mockReferenceData);
		Reference createdReference = referencesAssemblyImpl
				.updateReference(entityMapper.convertToReferenceBusinessEntity(mockReferenceData));
		assertThat(createdReference.referenceID(), equalTo(mockReferenceData.referenceID()));
		assertThat(createdReference.referenceID(), is(notNullValue()));
	}

	/*@Test(expected = DataUpdationException.class)
	public void givenRepositoryThrowsDataIntegrityViolationExceptionUpdateReferenceShouldThrowDataUpdateException() {
		when(referenceRepository.exists((any(String.class)))).thenReturn(true);
		when(referenceRepository.save(testDataHelper.getReferenceDataEntity()))
				.thenThrow(new DataIntegrityViolationException("test"));
		referencesAssemblyImpl.updateReference(testDataHelper.getReferenceBusinessEntity());
	}*/

	@Test(expected = DataUpdationException.class)
	public void givenRepositoryReturnsFalseForExistsUpdateReferenceShouldThrowDataUpdateException() {
		when(referenceRepository.exists(testDataHelper.getRandomAlphabeticString())).thenReturn(false);
		referencesAssemblyImpl.updateReference(testDataHelper.getReferenceBusinessEntity());
	}

	@Test(expected = DataUpdationException.class)
	public void givenRepositorySavesReferenceDataWithInvalidReferenceIDInUpdateReferenceShouldThrowDataUpdationException() {
		ReferenceData mockReferenceData = testDataHelper.getReferenceDataEntity();
		when(referenceRepository.save(any(ReferenceData.class))).thenReturn(mockReferenceData);
		Reference createdReference = referencesAssemblyImpl
				.updateReference(entityMapper.convertToReferenceBusinessEntity(mockReferenceData));
		assertThat(createdReference.referenceID(), equalTo(mockReferenceData.referenceID()));
		assertThat(createdReference.referenceID(), is(notNullValue()));
	}
	
	@Test
	public void givenRepositoryFetchReferenceDataWithTargetTypeAndMasterId() {
		final List<ReferenceData> referenceDataList = new ArrayList<>();
		referenceDataList.add(testDataHelper.getReferenceDataEntity());
		when(referenceRepository.findByMasterID(any(String.class)))
		.thenReturn(referenceDataList);
		List<Reference> references = referencesAssemblyImpl.findReferenceByMasterId(referenceDataList.get(0).masterID(), referenceDataList.get(0).referenceTypeData().referenceTypeID());
		assertThat(references.get(0).masterID() , equalTo(referenceDataList.get(0).masterID()));
		assertThat(references.get(0).referenceTypeID() , equalTo(referenceDataList.get(0).referenceTypeData().referenceTypeID()));

	}
	
	@Test(expected=DataAccessException.class)
	public void deleteReferenceByIDDataIntegrityViolationException() {
		doThrow(new DataIntegrityViolationException("Wrong reference ID")).when(referenceRepository).delete(any(String.class));
		referencesAssemblyImpl.deleteReferenceByID(testDataHelper.getRandomAlphabeticString());

	}
	
	//Find All
	@Test
	public void givenRepositoryReturnsNonEmptyPagedReferenceDataFindTypesShouldReturnCorrespondingReferencesList() {
		List<ReferenceData> referencesDataList = new ArrayList<>();
		ReferenceData referenceData = testDataHelper.getReferenceDataEntity();
		referencesDataList.add(referenceData);

		when(referenceRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(referencesDataList));
		List<Reference> referencesList = referencesAssemblyImpl.findReferences(new PageRequest(0, 5));
		assertThat(referencesList, hasSize(equalTo(referencesDataList.size())));
		assertThat(referencesList.get(0).nativeSourceIDValue(), equalTo(referenceData.nativeSourceIDValue()));
	}

	@Test
	public void givenRepositoryReturnsEmptyPagedReferenceDataFindTypesShouldReturnEmptyReferencesList() {
		when(referenceRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(new ArrayList<>()));
		List<Reference> referencesList = referencesAssemblyImpl.findReferences(new PageRequest(0, 5));
		assertThat(referencesList, hasSize(equalTo(0)));
	}

	@Test
	public void givenRepositoryReturnsNullFindTypeesShouldReturnEmptyReferenceTypesList() {
		when(referenceRepository.findAll(any(Pageable.class))).thenReturn(null);
		List<Reference> referencesList = referencesAssemblyImpl.findReferences(new PageRequest(0, 5));
		assertThat(referencesList, hasSize(equalTo(0)));
	}

	@Test(expected = UnknownException.class)
	public void givenRepositoryThrowsAnyExceptionFindTypesShouldThrowUnknownException() {
		when(referenceRepository.findAll(any(Pageable.class))).thenThrow(new RuntimeException());
		referencesAssemblyImpl.findReferences(new PageRequest(0, 5));
	}
	
/*	@Test
	public void givenRepositoryReturnsFetchReferencesTypeAndTargetTypeShouldReturnReferenceList() {
		ReferenceData mockReferenceData = testDataHelper.getReferenceDataEntity();
		when(referenceRepository.findByNativeSourceIDValueAndReferenceTypeDataReferenceTypeID(any(String.class),any(String.class))).thenReturn(mockReferenceData);
		List<Reference> referencesList = referencesAssemblyImpl.findReferencesTypeAndTargetType(entityMapper.convertToReferenceBusinessEntity(mockReferenceData));
		assertThat(referencesList.get(0).referenceTypeID(), equalTo(mockReferenceData.referenceTypeData().referenceTypeID()));
		assertThat(referencesList.get(0).targetReferenceTypeID(), equalTo(mockReferenceData.referenceTypeData().referenceTypeID()));
	}
*/	
	@Test
	public void givenRepositoryReturnsFetchReferenceByNativeSourceIDValueAndTypeShouldReturnReferences() {
		ReferenceData mockReferenceData = testDataHelper.getReferenceDataEntity();
		when(referenceRepository.findByNativeSourceIDValueAndReferenceTypeDataReferenceTypeID(any(String.class),any(String.class))).thenReturn(mockReferenceData);
		Reference references = referencesAssemblyImpl.findReferenceByNativeSourceIDValueAndType(entityMapper.convertToReferenceBusinessEntity(mockReferenceData));
		assertThat(references.referenceID(), equalTo(mockReferenceData.referenceID()));
		assertThat(references.referenceID(), is(notNullValue()));
	}


}
