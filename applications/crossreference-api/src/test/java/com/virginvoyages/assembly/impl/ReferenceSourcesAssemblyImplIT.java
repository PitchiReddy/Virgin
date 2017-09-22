package com.virginvoyages.assembly.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;
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
}
