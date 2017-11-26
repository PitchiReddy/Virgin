package com.virginvoyages.crossreference.client;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.crossreference.model.Reference;
import com.virginvoyages.crossreference.model.ReferenceType;
import com.virginvoyages.sailor.helper.TestDataHelper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CrossreferenceClientTest {
	
	@Autowired
	private CrossreferenceClient referenceClient;
	
	@Autowired
	private TestDataHelper testDataHelper;
	
	@Test
    public void findByTypeTestForExistingReferenceTypeID() {
    	
		Reference reference = testDataHelper.getReferenceMockObject();
    	List<Reference> listOfReference = referenceClient.findByType(reference).embedded().references();
    	assertThat(listOfReference, is(notNullValue()));
    	for(Reference ref: listOfReference) {
	    	assertThat(ref, is(notNullValue()));
	    	assertThat(ref.referenceTypeID(), equalTo(reference.referenceTypeID()));
	    	    	
	    }
     } 
	@Test
    public void findByReferenceTypeNameTestForExistingReferenceTypeName() {
		
		ReferenceType referenceTypeData = testDataHelper.getReferenceTypeData();
		ReferenceType referenceType = referenceClient.getReferenceTypeByName(referenceTypeData.referenceType());
		assertThat(referenceType, is(notNullValue()));
		assertThat(referenceType.referenceType(), equalTo(referenceTypeData.referenceType()));
		assertThat(referenceType.referenceTypeID(), equalTo(referenceTypeData.referenceTypeID()));
	}
	
  }
