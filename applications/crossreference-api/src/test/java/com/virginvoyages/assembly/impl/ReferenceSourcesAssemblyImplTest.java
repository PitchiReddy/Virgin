package com.virginvoyages.assembly.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.crossreference.helper.MockDataHelper;
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
	private MockDataHelper mockDataHelper;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void givenRepositoryReturnsValidReferenceSourceDatafindReferenceSourceByIDShouldReturnReferenceSource() {
		ReferenceSourceData mockReferenceSourceData = mockDataHelper.getMockReferenceSourceDataEntity();
		when(referenceSourceRepository.findOne((any(String.class)))).thenReturn(mockReferenceSourceData);
		ReferenceSource referenceSource = referenceSourcesAssemblyImpl
				.findReferenceSourceByID(mockDataHelper.getValidReferenceSourceByID());
		assertThat(referenceSource.referenceSourceID(), is(notNullValue()));
		assertThat(referenceSource.referenceSource(), equalTo(mockReferenceSourceData.referenceSource()));
	}

	@Test
	public void givenRepositorySavesReferenceSourceDataAddReferenceSourcesShouldReturnSavedEntity() {
		ReferenceSourceData mockReferenceSourceData = mockDataHelper.getMockReferenceSourceDataEntity();
		when(referenceSourceRepository.save(any(ReferenceSourceData.class))).thenReturn(mockReferenceSourceData);
		ReferenceSource createdReferenceSource = referenceSourcesAssemblyImpl.addReferenceSource(mockReferenceSourceData.convertToBusinessEntity());
		assertThat(createdReferenceSource, notNullValue());
		assertThat(createdReferenceSource.referenceSourceID(), notNullValue());
		assertThat(createdReferenceSource.referenceSource(), equalTo(mockReferenceSourceData.referenceSource()));
	}

	@Test
	public void givenValidReferenceSourcesUpdateReferenceSourcesShouldReturnUpdatedReferenceSources() {
		ReferenceSourceData mockReferenceSourceData = mockDataHelper.getMockReferenceSourceDataEntity();
		ReferenceSource mockUpdateReferenceSource = mockReferenceSourceData.convertToBusinessEntity().referenceSource(mockDataHelper.getDataForUpdateReferenceSource());
		when(referenceSourceRepository.save(any(ReferenceSourceData.class))).thenReturn(mockUpdateReferenceSource.convertToDataEntity());
		ReferenceSource updatedReferenceSource = referenceSourcesAssemblyImpl.updateReferenceSource(mockUpdateReferenceSource);
		assertThat(updatedReferenceSource.referenceSourceID(), is(notNullValue()));
		assertThat(updatedReferenceSource.referenceSource(), equalTo(mockUpdateReferenceSource.referenceSource()));
	}

}
