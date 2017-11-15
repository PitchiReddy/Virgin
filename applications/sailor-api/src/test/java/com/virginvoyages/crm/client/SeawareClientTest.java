/*package com.virginvoyages.crm.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.crm.data.SeawareData;
import com.virginvoyages.sailor.helper.TestDataHelper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SeawareClientTest {
	
	@Autowired
	private SeawareClient seawareClient;
	
	@Autowired
	private TestDataHelper testDataHelper;
	
	@Test
    public void findSeawareData() {
	SeawareData seawareData =	testDataHelper.genarateSeawaredataToCreate();
	seawareClient.findseawareData(seawareData);

  }
}
*/