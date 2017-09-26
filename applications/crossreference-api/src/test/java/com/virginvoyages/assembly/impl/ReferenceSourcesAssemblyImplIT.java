package com.virginvoyages.assembly.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsNull.notNullValue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.virginvoyages.assembly.ReferenceSourcesAssembly;
import com.virginvoyages.crossreference.helper.TestDataHelper;
import com.virginvoyages.crossreference.sources.ReferenceSource;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ReferenceSourcesAssemblyImplIT {

	@Autowired
	private ReferenceSourcesAssembly referenceSourcesAssembly;
	
	@Autowired
	private TestDataHelper testDataHelper;
	
	
	@Test
	public void givenValidReferenceSourceCreateReferenceSourceShouldReturnReferenceSource() {
		ReferenceSource referenceSource = testDataHelper.getDataForCreateReferenceSource();
		referenceSourcesAssembly.addReferenceSource(referenceSource);
		assertThat(referenceSource.referenceSourceID(), is(notNullValue()));
		assertThat(referenceSource.referenceSourceID(), equalTo("RS1"));
		assertThat(referenceSource.referenceSourceName(), equalTo("Seaware"));
	}
	
	@Test
	public void givenValidReferenceSourceFindReferenceSourceShouldReturnReferenceSource() {
		ReferenceSource referenceSource = testDataHelper.getDataForCreateReferenceSource();
		referenceSourcesAssembly.addReferenceSource(referenceSource);
		ReferenceSource findReferenceSource = referenceSourcesAssembly.findReferenceSourceByID(referenceSource.referenceSourceID());
		assertThat(findReferenceSource.referenceSourceID(), is(notNullValue()));
		assertThat(findReferenceSource.referenceSourceName(), equalTo("Seaware"));
	}
	
	@Test
	public void givenValidReferenceSourceDeleteReferenceSourceShouldDeleteReferenceSource() {
		ReferenceSource referenceSource = testDataHelper.getDataForCreateReferenceSource();
		referenceSourcesAssembly.deleteReferenceSourceByID(referenceSource.referenceSourceID());
		//assertThat(referenceSource.referenceSourceID(), is(nullValue()));
	}
	
	@Test
	public void givenValidReferenceSourceUpdateReferenceSourceShouldUpdateReferenceSource() {
		ReferenceSource referenceSource = testDataHelper.getDataForCreateReferenceSource();
		referenceSource.referenceSourceName("Updated Seaware");
		referenceSourcesAssembly.updateReferenceSource(referenceSource.referenceSourceID(), referenceSource);
		assertThat(referenceSource.referenceSourceName(), equalTo("Updated Seaware"));
	}
	
	@Test
	public void givenValidReferenceSourceFindSourcesShouldRetunsReferenceSources() {
		testDataHelper.getDataForCreateReferenceSource();
		List<ReferenceSource> referenceSourceList =referenceSourcesAssembly.findSources();
		assertThat(referenceSourceList, hasSize(5));
		for(ReferenceSource referenceSource: referenceSourceList) {
			assertThat(referenceSource.referenceSourceName(), equalTo("Seaware"));
			referenceSourcesAssembly.deleteReferenceSourceByID(referenceSource.referenceSourceID());
		}
		
	}
}
