package com.virginvoyages.assembly.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
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
		ReferenceSource createdReferenceSource = referenceSourcesAssembly.addReferenceSource(referenceSource);
		assertThat(createdReferenceSource.referenceSourceID(), is(notNullValue()));
		assertThat(createdReferenceSource.referenceSource(), equalTo(referenceSource.referenceSource()));
		assertThat(createdReferenceSource.inActive(), equalTo(referenceSource.inActive()));
		
	}
	
	@Test
	public void givenValidReferenceSourceFindReferenceSourceShouldReturnReferenceSource() {
		ReferenceSource referenceSource = testDataHelper.getDataForCreateReferenceSource();
		ReferenceSource createdReferenceSource =  referenceSourcesAssembly.addReferenceSource(referenceSource);
		ReferenceSource findReferenceSource = referenceSourcesAssembly.findReferenceSourceByID(createdReferenceSource.referenceSourceID());
		assertThat(findReferenceSource.referenceSourceID(), is(notNullValue()));
		assertThat(findReferenceSource.referenceSource(), equalTo(createdReferenceSource.referenceSource()));
		referenceSourcesAssembly.deleteReferenceSourceByID(findReferenceSource.referenceSourceID());
	}
	
	@Test
	public void givenValidReferenceSourceDeleteReferenceSourceShouldDeleteReferenceSource() {
		ReferenceSource referenceSource = testDataHelper.getDataForCreateReferenceSource();
		ReferenceSource createdReferenceSource =  referenceSourcesAssembly.addReferenceSource(referenceSource);
		referenceSourcesAssembly.deleteReferenceSourceByID(createdReferenceSource.referenceSourceID());
		ReferenceSource findReferenceSource = referenceSourcesAssembly.findReferenceSourceByID(createdReferenceSource.referenceSourceID());
		assertThat(findReferenceSource, is(nullValue()));
	}
	
	@Test
	public void givenValidReferenceSourceUpdateReferenceSourceShouldUpdateReferenceSource() {
		ReferenceSource createReferenceSource = testDataHelper.getDataForCreateReferenceSource();
		ReferenceSource updateReferenceSource = testDataHelper.getDataForUpdateReferenceSource(createReferenceSource);
		ReferenceSource updatedReferenceSource = referenceSourcesAssembly.updateReferenceSource(updateReferenceSource);
		ReferenceSource findReferenceSource = referenceSourcesAssembly.findReferenceSourceByID(updatedReferenceSource.referenceSourceID());
		assertThat(updatedReferenceSource.referenceSourceID(), equalTo(findReferenceSource.referenceSourceID()));
		assertThat(updatedReferenceSource.referenceSource(), equalTo(findReferenceSource.referenceSource()));
		referenceSourcesAssembly.deleteReferenceSourceByID(findReferenceSource.referenceSourceID());
	}
	
	@Test
	public void givenValidReferenceSourceFindSourcesShouldRetunsReferenceSources() {
		ReferenceSource createReferenceSource = testDataHelper.getDataForCreateReferenceSource();
		List<ReferenceSource> referenceSourceList =referenceSourcesAssembly.findSources();
		assertThat(referenceSourceList, hasSize(greaterThan(0)));
		for(ReferenceSource referenceSource: referenceSourceList) {
			assertThat(referenceSource.referenceSource(), equalTo(createReferenceSource.referenceSource()));
		}
		
	}
}
