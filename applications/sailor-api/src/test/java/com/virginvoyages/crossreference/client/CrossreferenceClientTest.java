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

import com.virginvoyages.model.crossreference.Reference;
import com.virginvoyages.sailor.helper.TestDataHelper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CrossreferenceClientTest {
	
	@Autowired
	private CrossreferenceClient referenceClient;
	
	@Autowired
	private TestDataHelper testDataHelper;
	
	@Test
    public void findBySourceTest() {
    	
		Reference referenceData = testDataHelper.generateReferenceSource();
    	List<Reference> listOfReference = referenceClient.findBySource(referenceData);
	    for(Reference reference: listOfReference) {
	    	assertThat(reference, is(notNullValue()));
	    	assertThat(reference.masterID(), equalTo(referenceData.masterID()));
	    	assertThat(reference.nativeSourceIDValue(), equalTo(referenceData.nativeSourceIDValue()));
	    	
	    }
    	
  }  
  
}
