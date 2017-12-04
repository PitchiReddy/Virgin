package com.virginvoyages.sailor;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.crm.data.AccountData;
import com.virginvoyages.crm.data.QueryResultsData;
import com.virginvoyages.sailor.helper.MockDataHelper;
import com.virginvoyages.sailor.helper.SailorMapper;
import com.virginvoyages.sailor.model.Sailor;
import com.virginvoyages.seaware.data.ClientData;

/**
 * Test class for SailorMapper
 * @author rpraveen
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SailorMapperTest {
	
	@Autowired
	private MockDataHelper mockDataHelper;
	
	@Autowired
	private SailorMapper sailorMapper;
	
	@Test
	public void mapAccountDataToSailorShouldMapAccountDataToSailorAttributes() {
		String firstName = "John";
		String lastName = "Mike";
		String salutation = "Mr.";

		AccountData accountData = new AccountData();
		accountData.firstName(firstName);
		accountData.lastName(lastName);
		accountData.salutation(salutation);
		Sailor sailor = sailorMapper.mapAccountDataToSailor(accountData);
		
		assertThat(sailor.firstName(), is(firstName));
		assertThat(sailor.lastName(), is(lastName));
		assertThat(sailor.salutation(), is(salutation));
	}
	
	@Test
	public void mapClientDataToSailorShouldMapClientDataToSailorAttributes() {
		String firstName = "John";
		String lastName = "Mike";
		String salutation = "Mr.";

		ClientData clientData = new ClientData();
		clientData.firstName(firstName);
		clientData.lastName(lastName);
		clientData.salutation(salutation);
		Sailor sailor = sailorMapper.mapClientDataToSailor(clientData);
		
		assertThat(sailor.firstName(), is(firstName));
		assertThat(sailor.lastName(), is(lastName));
		assertThat(sailor.salutation(), is(salutation));
	}
	
	@Test
	public void givenQueryResultsHasRecordsRetrieveListOfSailorIDsShouldReturnAllSailorIDsInQueryResults() {
		List<AccountData> accountList = new ArrayList<AccountData>();
		ArrayList<String> sailorIdsList = new ArrayList<String>();
		sailorIdsList.add("S1");
		sailorIdsList.add("S2");
		sailorIdsList.add("S3");
		accountList.add(mockDataHelper.generateAccountDataToCreate().id(sailorIdsList.get(0)));
		accountList.add(mockDataHelper.generateAccountDataToCreate().id(sailorIdsList.get(1)));
		accountList.add(mockDataHelper.generateAccountDataToCreate().id(sailorIdsList.get(2)));
		QueryResultsData<AccountData> queryResults = new QueryResultsData<AccountData>().records(accountList);
		List<String> sailorIds = sailorMapper.retrieveListOfSailorIDs(queryResults);
		assertThat(sailorIds,hasSize(3));
		assertThat(sailorIds.containsAll(sailorIdsList), is(true));
	}
	
	@Test
	public void givenQueryResultsHasNoRecordsRetrieveListOfSailorIDsShouldReturnEmptyList() {
		assertThat(sailorMapper.retrieveListOfSailorIDs(new QueryResultsData<AccountData>()),hasSize(0));
	}
	
	@Test
	public void mapSailorToAccountDataShouldMapSailorToAccountDataAttributes() {
		String firstName = "siva";
		String lastName = "Narthu";
		String salutation = "Mr.";
		String email = "shanku91.java@gmail.com";
		
		Sailor sailor = new Sailor();
		sailor.firstName(firstName);
		sailor.lastName(lastName);
		sailor.salutation(salutation);
		sailor.primaryEmail(email);
		
		AccountData accountdata = SailorMapper.mapSailorToAccountData(sailor);
		assertThat(accountdata.firstName(), is(firstName));
		assertThat(accountdata.lastName(), is(lastName));
		assertThat(accountdata.salutation(), is(salutation));
		assertThat(accountdata.primaryEmail(), is(email));
	}
	
}
