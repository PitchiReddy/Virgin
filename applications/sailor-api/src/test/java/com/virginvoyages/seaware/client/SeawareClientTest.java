package com.virginvoyages.seaware.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.sailor.helper.TestDataHelper;
import com.virginvoyages.seaware.data.OTAProfileReadRS;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SeawareClientTest {
	
	@Autowired
	private SeawareClient seawareClient;
	
	@Autowired
	private TestDataHelper testDataHelper;
	
	@Test
    public void findSeawareData() throws Exception {
    	OTAProfileReadRS otaProfileReadRS  = seawareClient.findseawareData(testDataHelper.genarateSeawaredataToCreate());
    	System.out.println("Data is ::::" + otaProfileReadRS.getProfiles());
    }
}
