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
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.crossreference.exceptions.DataInsertionException;
import com.virginvoyages.crossreference.exceptions.DataNotFoundException;
import com.virginvoyages.crossreference.helper.TestDataHelper;
import com.virginvoyages.crossreference.sources.ReferenceSource;
import com.virginvoyages.data.entities.ReferenceSourceData;
import com.virginvoyages.data.repositories.ReferenceSourceRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReferenceSourcesAssemblyImplTest {
	
	@Mock
	private ReferenceSourceRepository referenceSourceRepository;

	@InjectMocks
	private ReferenceSourcesAssemblyImpl referenceSourcesAssemblyImpl;

	@Autowired
	private TestDataHelper testDataHelper;
	
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void givenRepositoryReturnsSavedEntityAddReferenceSourcesShouldReturnSavedReference() {
		ReferenceSourceData mockReferenceSourceData = testDataHelper.getReferenceSourceDataEntity();
		when(referenceSourceRepository.save(any(ReferenceSourceData.class))).thenReturn(mockReferenceSourceData);
		ReferenceSource createdReferenceSource = referenceSourcesAssemblyImpl.addReferenceSource(mockReferenceSourceData.convertToBusinessEntity());
		assertThat(createdReferenceSource, notNullValue());
		assertThat(createdReferenceSource.referenceSourceID(), notNullValue());
		assertThat(createdReferenceSource.referenceSource(), equalTo(mockReferenceSourceData.referenceSource()));
	}
	
	@Test(expected = DataInsertionException.class)
	public void givenRepositoryReturnsNullAddReferenceSourcesShouldThrowDataInsertException() {
		when(referenceSourceRepository.save(any(ReferenceSourceData.class))).thenReturn(null);
		referenceSourcesAssemblyImpl.addReferenceSource(testDataHelper.getReferenceSourceBusinessEntity());
	}
	
	@Test(expected = DataInsertionException.class)
	public void givenRepositoryReturnsReferenceSourceDataWithEmptyIdAddReferenceSourcesShouldThrowDataInsertException() {
		when(referenceSourceRepository.save(any(ReferenceSourceData.class))).thenReturn(new ReferenceSourceData());
		referenceSourcesAssemblyImpl.addReferenceSource(testDataHelper.getReferenceSourceBusinessEntity());
	}
	
	@Test(expected = DataInsertionException.class)
	public void givenRepositoryThrowsAnyExceptionAddReferenceSourcesShouldThrowDataInsertException() {
		when(referenceSourceRepository.save(any(ReferenceSourceData.class))).thenThrow(new RuntimeException());
		referenceSourcesAssemblyImpl.addReferenceSource(testDataHelper.getReferenceSourceBusinessEntity());
	}
	
	@Test
	public void givenRepositoryReturnsValidReferenceSourceDatafindReferenceSourceByIDShouldReturnReferenceSource() {
		ReferenceSourceData mockReferenceSourceData = testDataHelper.getReferenceSourceDataEntity();
		when(referenceSourceRepository.findOne((any(String.class)))).thenReturn(mockReferenceSourceData);
		ReferenceSource referenceSource = referenceSourcesAssemblyImpl
				.findReferenceSourceByID(testDataHelper.getRandomAlphabeticString());
		assertThat(referenceSource.referenceSourceID(), is(notNullValue()));
		assertThat(referenceSource.referenceSource(), equalTo(mockReferenceSourceData.referenceSource()));
	}
	
	
	@Test(expected = DataNotFoundException.class)
	public void givenRepositoryReturnsNoReferenceSourceDatafindReferenceSourceByIDShouldThrowDataNotFoundException() {
		ReferenceSource findReferenceSource = referenceSourcesAssemblyImpl.findReferenceSourceByID(testDataHelper.getRandomAlphanumericString());
		assertThat(findReferenceSource, is(nullValue()));
	
	}
	
	@Test
	public void givenRepositorySavesReferenceSourceDataUpdateReferenceSourcesShouldReturnSavedEntity() {
		ReferenceSourceData mockReferenceSourceData = testDataHelper.getReferenceSourceDataEntity();
		when(referenceSourceRepository.findOne((any(String.class)))).thenReturn(mockReferenceSourceData);
		when(referenceSourceRepository.save(any(ReferenceSourceData.class))).thenReturn(mockReferenceSourceData);
		ReferenceSource createdReferenceSource = referenceSourcesAssemblyImpl.updateReferenceSource(mockReferenceSourceData.convertToBusinessEntity());
		assertThat(createdReferenceSource, notNullValue());
		assertThat(createdReferenceSource.referenceSourceID(), notNullValue());
		assertThat(createdReferenceSource.referenceSource(), equalTo(mockReferenceSourceData.referenceSource()));
	}
	
	
	@Test
	public void givenRepositoryReturnsLisyOfReferenceSourceDataFindSourcesShouldReturnCorrespondingReferenceSources() {
		List<ReferenceSourceData> mockReferenceSourceDataList = new ArrayList<ReferenceSourceData>();
		mockReferenceSourceDataList.add(testDataHelper.getReferenceSourceDataEntity());
		mockReferenceSourceDataList.add(testDataHelper.getReferenceSourceDataEntity());
		
		when(referenceSourceRepository.findAll()).thenReturn(mockReferenceSourceDataList);
		List<ReferenceSource> referenceSourceList = referenceSourcesAssemblyImpl.findSources();
		assertThat(referenceSourceList, hasSize(equalTo(mockReferenceSourceDataList.size())));
	}
	
	@Test(expected = DataNotFoundException.class)
	public void givenRepositoryReturnsValidReferenceSourceDataDeleteReferenceSourceByIDShouldReturnEmptyReferenceType() {
		Mockito.spy(ReferenceSourceData.class);
		referenceSourcesAssemblyImpl.deleteReferenceSourceByID(testDataHelper.getRandomAlphanumericString());
		ReferenceSource findReferenceSource = referenceSourcesAssemblyImpl.findReferenceSourceByID(testDataHelper.getRandomAlphanumericString());
		assertThat(findReferenceSource, is(nullValue()));
	}
	
	
}
