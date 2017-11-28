package com.virginvoyages.assembly.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.crm.client.AccountClient;
import com.virginvoyages.crm.client.QueryClient;
import com.virginvoyages.crm.data.AccountData;
import com.virginvoyages.crm.data.QueryResultsData;
import com.virginvoyages.crm.data.RecordTypeData;
import com.virginvoyages.crossreference.client.CrossreferenceClient;
import com.virginvoyages.crossreference.model.ReferenceType;
import com.virginvoyages.sailor.exceptions.AccountCreationException;
import com.virginvoyages.sailor.helper.MockDataHelper;
import com.virginvoyages.sailor.helper.SailorMapper;
import com.virginvoyages.sailor.helper.SailorQueryHelper;
import com.virginvoyages.sailor.model.Sailor;
import com.virginvoyages.seaware.dao.SeawareDAO;
import com.virginvoyages.seaware.data.ClientData;

/**
 * @author rpraveen 
 * Test Class for SailorAssembly Implementation
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class SailorAssemblyImplTest {

	@Mock
    private QueryClient queryClient;
	
	@Mock
    private AccountClient accountClient;
	
	@Mock
    private SailorQueryHelper sailorQueryHelper;
	
	@Autowired
	@Spy
	private SailorMapper sailorMapper;
	
	@InjectMocks
	private SailorAssemblyImpl sailorAssembly;
	
	@Mock
	private PreferenceAssemblyImpl preferenceAssembly;

	@Autowired
	private MockDataHelper mockDataHelper;
	
	@Mock 
	private CrossreferenceClient xrefClient;
	
	@Mock 
	private SeawareDAO seawareDAO;
	
	
	@Before
    public void setUp() throws Exception {
         MockitoAnnotations.initMocks(this);
    }
	

	@Test
	public void givenSailorWithoutPreferencesExistsWithSailorIDGetSailorbyIdShouldReturnSailorWithEmptyPreferences() {
		
		AccountData accountData = mockDataHelper.generateAccountDataToCreate();
		when(accountClient.findAccount(any(String.class))).thenReturn(accountData);
		
		Sailor sailor = sailorAssembly.getSailorById(any(String.class));

		assertThat(accountData.firstName(), equalTo(sailor.firstName()));
		assertThat(accountData.lastName(), equalTo(sailor.lastName()));
		assertThat(accountData.primaryEmail(), equalTo(sailor.primaryEmail()));
		assertThat(sailor.preferences(), is(notNullValue()));
		assertThat(sailor.preferences(), hasSize(0));
	}
	
	@Test
	public void givenSailorWithPreferencesExistsWithSailorIDGetSailorbyIdShouldReturnSailorWithPreferences() {

		when(accountClient.findAccount(any(String.class))).thenReturn(new AccountData());
		when(preferenceAssembly.findSailorPreferences("")).thenReturn(mockDataHelper.getPreferencesEmbedded(true));
				
		Sailor sailor = sailorAssembly.getSailorById("");
		
		assertThat(sailor.preferences().size(), not(equalTo(0)));
	}

   	@Test
	public void givenSailorsExistWithMatchingDataFindSailorsShouldReturnListOfSailors() {
	
		AccountData accountData = mockDataHelper.generateAccountDataToCreate();
				
		when(sailorQueryHelper.generateFindQueryString(accountData)).thenReturn("");
		when(queryClient.findAccounts("")).thenReturn(mockDataHelper.getQueryResultsDataWithThreeAccountDataRecordsWithSameData());
		
		List<AccountData> accountsList = mockDataHelper.getQueryResultsDataWithThreeAccountDataRecordsWithSameData().records();
		
		for (AccountData account : accountsList) {
			when(accountClient.findAccount(account.id())).thenReturn(account);
		}
		
		List<Sailor> listOfSailors = sailorAssembly.findSailors(accountData);
		
		assertThat(listOfSailors, hasSize(3));
		for (Sailor sailor : listOfSailors) {
			assertThat(sailor.firstName(), equalTo(accountData.firstName()));
		}
	}

	@Test
	public void givenSailorsDoNotExistWithMatchingDataFindSailorsShouldReturnEmptyList() {
		
		when(sailorQueryHelper.generateFindQueryString(any(AccountData.class))).thenReturn("");
		AccountData accountData = mockDataHelper.generateAccountDataToCreate();
		when(queryClient.findAccounts(any(String.class))).thenReturn(new QueryResultsData<AccountData>());
		List<Sailor> listOfSailors = sailorAssembly.findSailors(accountData);
		
		assertThat(listOfSailors, hasSize(0));
		
	}

	@Test
	public void whenSailorCreatedCreateSailorShouldReturnCreatedSailor() {

		AccountData accountData = mockDataHelper.generateAccountDataToCreate();
		
		when(queryClient.findSailorAccountRecordTypeID()).thenReturn(new QueryResultsData<RecordTypeData>());
		when(accountClient.createAccount(accountData)).thenReturn(mockDataHelper.getAccountCreateStatus("true"));
		when(accountClient.findAccount(any(String.class))).thenReturn(accountData);

		Sailor sailor = sailorAssembly.createSailor(accountData);

		assertThat(accountData.firstName(), equalTo(sailor.firstName()));
		assertThat(accountData.lastName(), equalTo(sailor.lastName()));
		assertThat(accountData.dateofBirth(), equalTo(sailor.dateofBirth()));
		assertThat(accountData.primaryEmail(), equalTo(sailor.primaryEmail()));
		assertThat(accountData.mobileNumber(), equalTo(sailor.mobileNumber()));
		
	}
	
	@Test(expected=AccountCreationException.class)
	public void whenSailorNotCreatedCreateSailorShouldThrowAccountCreationException() {

        AccountData accountData = mockDataHelper.generateAccountDataToCreate();
		
		when(queryClient.findSailorAccountRecordTypeID()).thenReturn(new QueryResultsData<RecordTypeData>());
		when(accountClient.createAccount(accountData)).thenReturn(mockDataHelper.getAccountCreateStatus("false"));
		when(accountClient.findAccount(any(String.class))).thenReturn(accountData);

		sailorAssembly.createSailor(accountData);
		
	}
		
	//Orchestration tests
	
	/*@Test
	public void givenAccountClientFindAccountReturnsAccountDataGetSalesforceAccountDataShouldReturnAccountData() {
		
	}
	
	@Test
	public void givenAccountClientFindAccountThrowsExceptionGetSalesforceAccountDataShouldReturnNull() {
		
	}*/
	
	//tests for getSeawareClientData
	@Test
	public void givenSeawareClientIDIsNullGetSeawareClientDataShouldReturnNull() {
		when(seawareDAO.getSeawareClientData(any(String.class))).thenReturn(new ClientData());
		assertThat(sailorAssembly.getSeawareClientData(null),nullValue());
		
	}
	
	/*@Test
	public void givenSeawareDAOGetSeawareClientDataClientIDReturnsClientDataGetSeawareClientDataShouldReturnClientData() {
		when(seawareDAO.getSeawareClientData(any(String.class))).thenReturn(new ClientData());
		assertThat(sailorAssembly.getSeawareClientData(any(String.class)),notNullValue());
	}*/
	
	//test for getReferenceTypeIDForName
	@Test
	public void givenCrossReferenceClientReturnsReferenceTypeGetReferenceTypeIDForNameShouldReturnReferenceTypeID() {
		ReferenceType type = new ReferenceType().referenceTypeID("test_type");
		when(xrefClient.getReferenceTypeByName(any(String.class))).thenReturn(type);
		assertThat(sailorAssembly.getReferenceTypeIDForName("dummy"), equalTo(type.referenceTypeID()));
	}
	
	@Test
	public void givenCrossReferenceClientReturnsNullGetReferenceTypeIDForNameShouldReturnNull() {
		when(xrefClient.getReferenceTypeByName(any(String.class))).thenReturn(null);
		assertThat(sailorAssembly.getReferenceTypeIDForName("dummy"), nullValue());
	}
	
}
