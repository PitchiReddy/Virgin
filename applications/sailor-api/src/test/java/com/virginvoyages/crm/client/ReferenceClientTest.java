package com.virginvoyages.crm.client;

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
import com.virginvoyages.crm.data.ReferenceData;
import com.virginvoyages.sailor.helper.TestDataHelper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReferenceClientTest {
	
	@Autowired
	private ReferenceClient referenceClient;
	
	@Autowired
	private TestDataHelper testDataHelper;
	
	@Test
    public void findBySourceTest() {
    	
		ReferenceData referenceData = testDataHelper.generateReferenceSource();
    	List<Reference> listOfReference = referenceClient.findBySource(referenceData);
	    for(Reference reference: listOfReference) {
	    	assertThat(reference, is(notNullValue()));
	    	assertThat(reference.masterID(), equalTo("M1"));
	    	assertThat(reference.nativeSourceIDValue(), equalTo("NSID1"));
	    	
	    }
    	
  }  
  
}
