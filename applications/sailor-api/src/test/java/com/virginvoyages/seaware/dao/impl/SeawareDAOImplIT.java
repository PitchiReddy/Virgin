package com.virginvoyages.seaware.dao.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.sailor.helper.TestDataHelper;
import com.virginvoyages.seaware.dao.SeawareDAO;
import com.virginvoyages.seaware.data.ClientData;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SeawareDAOImplIT {

	@Autowired
	private SeawareDAO seawareDAOImpl;
	
	@Autowired
	private TestDataHelper testDataHelper;

	@Test
	public void givenSeawareProfileExistsWithClientIDGetSeawareClientDataShouldReturnClientData() {
		String clientID = testDataHelper.getValidSeawareClientID();
		ClientData clientData = seawareDAOImpl.getSeawareClientData(clientID);
		assertThat(clientID, equalTo(clientData.id()));
	}
	
	@Test
	public void givenSeawareProfileDoesNotExistWithClientIDGetSeawareClientDataShouldReturnNull() {
		assertThat(seawareDAOImpl.getSeawareClientData("dummy"), nullValue());
	}
	
	
	
}
