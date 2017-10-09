package com.virginvoyages.assembly.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.virginvoyages.crossreference.helper.MockDataHelper;
import com.virginvoyages.crossreference.sources.ReferenceSource;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ReferenceSourcesAssemblyImplTest {


		
		
	    @InjectMocks
	    private ReferenceSourcesAssemblyImpl referenceSourcesAssemblyImpl;
		
		@Autowired
		private MockDataHelper mockDataHelper;
		
		@Before
	    public void setUp() throws Exception {
	         MockitoAnnotations.initMocks(this);
	    }
		
		@Test
		public void givenValidReferenceSourcesFindReferenceSourcesShouldReturnReferenceSources() {
	//		when(referenceSourcesDAOImpl.findReferenceSourceByID(any(String.class))).thenReturn(mockDataHelper.getReferenceSourceByID());
			ReferenceSource referenceSource = referenceSourcesAssemblyImpl.findReferenceSourceByID(mockDataHelper.getValidReferenceSourceByID());
			assertThat(referenceSource.referenceSourceID(), is(notNullValue()));
	        assertThat(referenceSource.referenceSourceName(), equalTo("Seaware"));
		}

		
		@Test
		public void givenValidReferenceSourcesAddReferenceSourcesShouldReturnReferenceSources() {
	//		Mockito.mock(ReferenceSourcesDAOImpl.class);
			ReferenceSource referenceSource = mockDataHelper.getDataForCreateReferenceSource();
			referenceSourcesAssemblyImpl.addReferenceSource(referenceSource);
			assertThat(referenceSource.referenceSourceID(), is(notNullValue()));
	        assertThat(referenceSource.referenceSourceName(), equalTo("Seaware"));
		}
		
		@Test
		public void givenValidReferenceSourcesUpdateReferenceSourcesShouldReturnUpdatedReferenceSources() {
			ReferenceSource referenceSource = mockDataHelper.getDataForCreateReferenceSource();
			referenceSource.referenceSourceName("Updated Seaware");
			referenceSourcesAssemblyImpl.updateReferenceSource(referenceSource.referenceSourceID(), referenceSource);
			assertThat(referenceSource.referenceSourceID(), is(notNullValue()));
	        assertThat(referenceSource.referenceSourceName(), equalTo("Updated Seaware"));
		}

}
