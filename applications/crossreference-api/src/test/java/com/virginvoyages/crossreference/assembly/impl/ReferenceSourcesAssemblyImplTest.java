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
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.crossreference.data.entities.ReferenceSourceData;
import com.virginvoyages.crossreference.data.repositories.ReferenceSourceRepository;
import com.virginvoyages.crossreference.helper.CrossReferenceEntityMapper;
import com.virginvoyages.crossreference.helper.TestDataHelper;
import com.virginvoyages.crossreference.model.ReferenceSource;
import com.virginvoyages.exception.DataAccessException;
import com.virginvoyages.exception.DataInsertionException;
import com.virginvoyages.exception.DataNotFoundException;
import com.virginvoyages.exception.DataUpdationException;
import com.virginvoyages.exception.UnknownException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReferenceSourcesAssemblyImplTest {

	@Mock
	private ReferenceSourceRepository referenceSourceRepository;

	@InjectMocks
	private ReferenceSourcesAssemblyImpl referenceSourcesAssemblyImpl;

	@Autowired
	private TestDataHelper testDataHelper;
	
	@Spy
	private CrossReferenceEntityMapper entityMapper;


	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	//Add
	@Test
	public void givenRepositoryReturnsSavedEntityAddReferenceSourcesShouldReturnSavedReference() {
		ReferenceSourceData mockReferenceSourceData = testDataHelper.getReferenceSourceDataEntity();
		when(referenceSourceRepository.save(any(ReferenceSourceData.class))).thenReturn(mockReferenceSourceData);
		ReferenceSource createdReferenceSource = referenceSourcesAssemblyImpl.addReferenceSource(
				entityMapper.convertToReferenceSourceBusinessEntity(mockReferenceSourceData));
		assertThat(createdReferenceSource, notNullValue());
		assertThat(createdReferenceSource.referenceSourceID(), notNullValue());
		assertThat(createdReferenceSource.referenceSource(), equalTo(mockReferenceSourceData.referenceSource()));
	}

	public void givenRepositoryReturnsNullAddReferenceSourcesShouldReturnNull() {
		when(referenceSourceRepository.save(testDataHelper.getReferenceSourceDataEntity())).thenReturn(null);
		assertThat(referenceSourcesAssemblyImpl.addReferenceSource(
				testDataHelper.getReferenceSourceBusinessEntity()), nullValue());
	}

	public void givenRepositoryReturnsReferenceSourceDataWithEmptyIdAddReferenceSourcesShouldReturnNull() {
		when(referenceSourceRepository.save(testDataHelper.getReferenceSourceDataEntity())).thenReturn(new ReferenceSourceData());
		assertThat(referenceSourcesAssemblyImpl.addReferenceSource(
				testDataHelper.getReferenceSourceBusinessEntity()), nullValue());
	}

	@Test(expected = DataInsertionException.class)
	public void givenRepositoryThrowsDataIntegrityViolationExceptionAddReferenceSourcesShouldThrowDataInsertException() {
		when(referenceSourceRepository.save(any(ReferenceSourceData.class))).thenThrow(new DataIntegrityViolationException("test"));
		referenceSourcesAssemblyImpl.addReferenceSource(testDataHelper.getReferenceSourceBusinessEntity());
	}

	@Test(expected = UnknownException.class)
	public void givenRepositoryThrowsAnyExceptionAddReferenceSourcesShouldThrowDataInsertException() {
		when(referenceSourceRepository.save(new ReferenceSourceData())).thenThrow(new RuntimeException());
		referenceSourcesAssemblyImpl.addReferenceSource(testDataHelper.getReferenceSourceBusinessEntity());
	}

	//Find By ID
	@Test
	public void givenRepositoryReturnsValidReferenceSourceDatafindReferenceSourceByIDShouldReturnReferenceSource() {
		ReferenceSourceData mockReferenceSourceData = testDataHelper.getReferenceSourceDataEntity();
		when(referenceSourceRepository.findOne((any(String.class)))).thenReturn(mockReferenceSourceData);
		ReferenceSource referenceSource = referenceSourcesAssemblyImpl
				.findReferenceSourceByID(testDataHelper.getRandomAlphabeticString());
		assertThat(referenceSource.referenceSourceID(), is(notNullValue()));
		assertThat(referenceSource.referenceSource(), equalTo(mockReferenceSourceData.referenceSource()));
	}

	@Test
	public void givenRepositoryReturnsNullfindReferenceSourceByIDShouldReturnNull() {
		when(referenceSourceRepository.findOne((any(String.class)))).thenReturn(null);
		assertThat(referenceSourcesAssemblyImpl.findReferenceSourceByID(
				testDataHelper.getRandomAlphanumericString()), is(nullValue()));

	}

	@Test(expected = UnknownException.class)
	public void givenRepositoryThrowsAnyExceptionFindReferenceByIDShouldThrowUnknownException() {
		when(referenceSourceRepository.findOne(any(String.class))).thenThrow(new RuntimeException());
		referenceSourcesAssemblyImpl.findReferenceSourceByID((testDataHelper.getRandomAlphabeticString()));
	}

	//Find By Name
	@Test
	public void givenRepositoryReturnsValidReferenceSourceDatafindByReferenceSourceNameShouldReturnReferenceSource() {
		ReferenceSourceData mockReferenceSourceData = testDataHelper.getReferenceSourceDataEntity();
		when(referenceSourceRepository.findByReferenceSource((any(String.class)))).thenReturn(mockReferenceSourceData);
		ReferenceSource referenceSource = referenceSourcesAssemblyImpl
				.findReferenceSourceByName(testDataHelper.getRandomAlphabeticString());
		assertThat(referenceSource.referenceSourceID(), is(notNullValue()));
		assertThat(referenceSource.referenceSource(), equalTo(mockReferenceSourceData.referenceSource()));
	}

	@Test
	public void givenRepositoryReturnsNullfindReferenceSourceByNameShouldReturnNull() {
		when(referenceSourceRepository.findByReferenceSource((any(String.class)))).thenReturn(null);
		assertThat(referenceSourcesAssemblyImpl.findReferenceSourceByName(
				testDataHelper.getRandomAlphanumericString()), is(nullValue()));
	}

	@Test(expected = UnknownException.class)
	public void givenRepositoryThrowsAnyExceptionFindReferenceSourceByNameShouldThrowUnknownException() {
		when(referenceSourceRepository.findByReferenceSource((any(String.class)))).thenThrow(new RuntimeException());
		assertThat(referenceSourcesAssemblyImpl.findReferenceSourceByName(
				testDataHelper.getRandomAlphanumericString()), is(nullValue()));
	}

	//Delete
	@Test
	public void givenDeleteOnRepositoryDoesNotThrowAnyExceptionDeleteReferenceSourceByIDShouldReturnTrue() {
		//Do nothing for refereenceSourceRepository.delete
		doNothing().when(referenceSourceRepository).delete(testDataHelper.getRandomAlphabeticString());
		assert(referenceSourcesAssemblyImpl.deleteReferenceSourceByID(testDataHelper.getRandomAlphanumericString()));

	}

	@Test(expected = DataNotFoundException.class)
	public void givenDeleteOnRepositoryThrowsEmptyResultDataAccessExceptionDeleteReferenceSourceByIDShouldThrowDataNotFoundException() {
		doThrow(new EmptyResultDataAccessException(1)).when(referenceSourceRepository).delete(any(String.class));
		referenceSourcesAssemblyImpl.deleteReferenceSourceByID(testDataHelper.getRandomAlphanumericString());

	}

	@Test(expected = DataAccessException.class)
	public void givenDeleteOnRepositoryThrowsDataIntegrityViolationExceptionDeleteReferenceSourceByIDShouldThrowDataAccessException() {
		doThrow(new DataIntegrityViolationException("test")).when(referenceSourceRepository).delete(any(String.class));
		referenceSourcesAssemblyImpl.deleteReferenceSourceByID(testDataHelper.getRandomAlphanumericString());

	}

	@Test(expected = UnknownException.class)
	public void givenDeleteOnRepositoryThrowsAnyExceptionDeleteReferenceSourceByIDShouldThrowUnknownException() {
		doThrow(new RuntimeException()).when(referenceSourceRepository).delete(any(String.class));
		referenceSourcesAssemblyImpl.deleteReferenceSourceByID((testDataHelper.getRandomAlphabeticString()));
	}

	// Update
	@Test(expected=DataUpdationException.class)
	public void givenRepositoryReturnsFalseForExistsUpdateReferenceSourcesShouldThrowDataUpdateException() {
		when(referenceSourceRepository.exists(testDataHelper.getRandomAlphabeticString())).thenReturn(false);
		referenceSourcesAssemblyImpl.updateReferenceSource(testDataHelper.getReferenceSourceBusinessEntity());
	}

	@Test(expected=DataUpdationException.class)
    public void givenRepositoryThrowsDataIntegrityViolationExceptionUpdateReferenceSourcesShouldThrowDataUpdateException() {
		when(referenceSourceRepository.exists((any(String.class)))).thenReturn(true);
		when(referenceSourceRepository.save(testDataHelper.getReferenceSourceDataEntity())).thenThrow(new DataIntegrityViolationException("test"));
		referenceSourcesAssemblyImpl.updateReferenceSource(testDataHelper.getReferenceSourceBusinessEntity());
	}

	@Test(expected=UnknownException.class)
    public void givenRepositoryThrowsAnyOtherExceptionUpdateReferenceSourcesShouldThrowUnknownException() {
		when(referenceSourceRepository.exists((any(String.class)))).thenReturn(true);
		when(referenceSourceRepository.save(testDataHelper.getReferenceSourceDataEntity())).thenThrow(new RuntimeException());
		referenceSourcesAssemblyImpl.updateReferenceSource(testDataHelper.getReferenceSourceBusinessEntity());
	}

	@Test
	public void givenRepositoryReturnsTrueForExistsAndRepositoryReturnsUpdatedSourceUpdateReferenceSourcesShouldReturnUpdatedEntity() {
		ReferenceSourceData mockReferenceSourceData = testDataHelper.getReferenceSourceDataEntity();
		when(referenceSourceRepository.exists((any(String.class)))).thenReturn(true);
		when(referenceSourceRepository.save(any(ReferenceSourceData.class))).thenReturn(mockReferenceSourceData);
		ReferenceSource createdReferenceSource = referenceSourcesAssemblyImpl.updateReferenceSource(
				entityMapper.convertToReferenceSourceBusinessEntity(mockReferenceSourceData));
		assertThat(createdReferenceSource, notNullValue());
		assertThat(createdReferenceSource.referenceSourceID(), notNullValue());
		assertThat(createdReferenceSource.referenceSource(), equalTo(mockReferenceSourceData.referenceSource()));
	}

	@Test
	public void givenRepositoryReturnsTrueForExistsAndRepositoryReturnsNullUpdateReferenceSourceShouldReturnNull() {
		when(referenceSourceRepository.exists((any(String.class)))).thenReturn(true);
		when(referenceSourceRepository.save(any(ReferenceSourceData.class))).thenReturn(null);
		assertThat(referenceSourcesAssemblyImpl
				.updateReferenceSource(testDataHelper.getReferenceSourceBusinessEntity()), nullValue());
	}

	@Test
	public void givenRepositoryReturnsTrueForExistsAndRepositoryReturnsEntityWithNullIDUpdateReferenceTypeShouldReturnNull() {
		when(referenceSourceRepository.exists((any(String.class)))).thenReturn(true);
		when(referenceSourceRepository.save(any(ReferenceSourceData.class))).thenReturn(testDataHelper.getReferenceSourceDataEntity().referenceSourceID(null));
		assertThat(referenceSourcesAssemblyImpl
				.updateReferenceSource(testDataHelper.getReferenceSourceBusinessEntity()), nullValue());
	}

	//Find All
	@Test
	public void givenRepositoryReturnsNonEmptyPagedReferenceSourceDataFindSourcesShouldReturnCorrespondingReferenceSourcesList() {
		List<ReferenceSourceData> referenceSourceDataList = new ArrayList<>();
		ReferenceSourceData referenceSourceData = testDataHelper.getReferenceSourceDataEntity();
		referenceSourceDataList.add(referenceSourceData);

		when(referenceSourceRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(referenceSourceDataList));
		List<ReferenceSource> referenceSourceList = referenceSourcesAssemblyImpl.findSources(new PageRequest(0, 5));
		assertThat(referenceSourceList, hasSize(equalTo(referenceSourceDataList.size())));
		assertThat(referenceSourceList.get(0).referenceSource(), equalTo(referenceSourceData.referenceSource()));
	}

	@Test
	public void givenRepositoryReturnsEmptyPagedReferenceSourceDataFindSourcesShouldReturnEmptyReferenceSourcesList() {
		when(referenceSourceRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(new ArrayList<>()));
		List<ReferenceSource> referenceSourceList = referenceSourcesAssemblyImpl.findSources(new PageRequest(0, 5));
		assertThat(referenceSourceList, hasSize(equalTo(0)));
	}

	@Test
	public void givenRepositoryReturnsNullFindSourcesShouldReturnEmptyReferenceSourcesList() {
		when(referenceSourceRepository.findAll(any(Pageable.class))).thenReturn(null);
		List<ReferenceSource> referenceSourceList = referenceSourcesAssemblyImpl.findSources(new PageRequest(0, 5));
		assertThat(referenceSourceList, hasSize(equalTo(0)));
	}

	@Test(expected = UnknownException.class)
	public void givenRepositoryThrowsAnyExceptionFindSourcesShouldThrowUnknownException() {
		when(referenceSourceRepository.findAll(any(Pageable.class))).thenThrow(new RuntimeException());
		referenceSourcesAssemblyImpl.findSources(new PageRequest(0, 5));
	}

}
