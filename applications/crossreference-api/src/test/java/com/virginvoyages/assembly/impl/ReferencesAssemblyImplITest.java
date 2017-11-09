package com.virginvoyages.assembly.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
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
import org.springframework.test.context.junit4.SpringRunner;
import com.virginvoyages.crossreference.exceptions.DataNotFoundException;
import com.virginvoyages.crossreference.exceptions.DataUpdationException;
import com.virginvoyages.crossreference.helper.TestDataHelper;
import com.virginvoyages.crossreference.references.Reference;
import com.virginvoyages.data.entities.ReferenceData;
import com.virginvoyages.data.repositories.ReferenceRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReferencesAssemblyImplITest {

	@Mock
	private ReferenceRepository referenceRepository;

	@InjectMocks
	private ReferencesAssemblyImpl referencesAssemblyImpl;

	@Autowired
	private TestDataHelper testDataHelper;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
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

	@Test(expected = DataNotFoundException.class)
	public void givenRepositoryReturnsNoReferenceDatafindReferenceByIDShouldThrowDataNotFoundException() {
		Reference findReference = referencesAssemblyImpl.findReferenceByID(testDataHelper.getRandomAlphabeticString());
		assertThat(findReference.referenceID(), is(nullValue()));
	}

	@Test
	public void givenRepositoryReturnsListOfReferenceDataFindReferencesShouldReturnCorrespondingReferences() {

		List<ReferenceData> mockReferenceList = new ArrayList<ReferenceData>();
		mockReferenceList.add(testDataHelper.getReferenceDataEntity());
		mockReferenceList.add(testDataHelper.getReferenceDataEntity());

		when(referenceRepository.findAll()).thenReturn(mockReferenceList);
		List<Reference> referenceList = referencesAssemblyImpl.findReferences();
		assertThat(referenceList, hasSize(equalTo(mockReferenceList.size())));
	}

	@Test
	public void givenRepositoryReturnsTrueForExistsAndRepositoryUpdatessuccessfullyUpdateReferenceShouldReturnUpdatedEntity() {
		ReferenceData mockReferenceData = testDataHelper.getReferenceDataEntity();
		when(referenceRepository.exists((any(String.class)))).thenReturn(true);
		when(referenceRepository.save(any(ReferenceData.class))).thenReturn(mockReferenceData);
		Reference createdReference = referencesAssemblyImpl
				.updateReference(mockReferenceData.convertToBusinessEntity());
		assertThat(createdReference.referenceID(), equalTo(mockReferenceData.referenceID()));
		assertThat(createdReference.referenceID(), is(notNullValue()));
	}

	@Test(expected = DataUpdationException.class)
	public void givenRepositoryThrowsDataIntegrityViolationExceptionUpdateReferenceShouldThrowDataUpdateException() {
		when(referenceRepository.exists((any(String.class)))).thenReturn(true);
		when(referenceRepository.save(testDataHelper.getReferenceDataEntity()))
				.thenThrow(new DataIntegrityViolationException("test"));
		referencesAssemblyImpl.updateReference(testDataHelper.getReferenceBusinessEntity());
	}

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
				.updateReference(mockReferenceData.convertToBusinessEntity());
		assertThat(createdReference.referenceID(), equalTo(mockReferenceData.referenceID()));
		assertThat(createdReference.referenceID(), is(notNullValue()));
	}
}
