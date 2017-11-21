package com.virginvoyages.assembly.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.assembly.SailorAssembly;
import com.virginvoyages.crm.data.AccountData;
import com.virginvoyages.sailor.Sailor;
import com.virginvoyages.exceptions.DataNotFoundException;
import com.virginvoyages.sailor.helper.TestDataHelper;

/**
 * @author rpraveen 
 * Test Class for SailorAssembly Implementation
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class SailorAssemblyImplIT {

	@Autowired
	private SailorAssembly sailorAssembly;

	@Autowired
	private TestDataHelper testDataHelper;

	@Test
	public void givenValidSailorIDWithoutPreferencesGetSailorbyIdShouldReturnSailorWithEmptyPreferences() {

		Sailor testSailor = testDataHelper.createTestSailor();

		Sailor sailor = sailorAssembly.getSailorById(testSailor.id());

		assertThat(testSailor.firstName(), equalTo(sailor.firstName()));
		assertThat(testSailor.lastName(), equalTo(sailor.lastName()));
		assertThat(testSailor.primaryEmail(), equalTo(sailor.primaryEmail()));
		assertThat(sailor.preferences().size(), equalTo(0));

		testDataHelper.deleteSailor(testSailor.id());
	}
	
	@Test
	public void givenValidSailorIDWithPreferecesGetSailorbyIdShouldReturnSailorWithPreferences() {

		String sailorID = testDataHelper.getSailorIDWithPreferences();
		Sailor sailor = sailorAssembly.getSailorById(sailorID);

		assertThat(sailor.preferences().size(), not(equalTo(0)));
	}

	@Test(expected = DataNotFoundException.class)
	public void givenSailorIDInValidFindSailorbyIDShouldThrowDataNotFoundException() {
		sailorAssembly.getSailorById(testDataHelper.getInvalidSailorID());
	}

	@Test(expected = DataNotFoundException.class)
	public void givenSailorIDValidDeleteSailorbyIDShouldDeleteUser() {
		Sailor createSailor = testDataHelper.createTestSailor();
		sailorAssembly.deleteSailorById(createSailor.id());
		sailorAssembly.getSailorById(createSailor.id());
	}

	@Test(expected = DataNotFoundException.class)
	public void givenSailorIDInValidDeleteSailorbyIDShouldThrowDataNotFoundException() {
		sailorAssembly.deleteSailorById(testDataHelper.getInvalidSailorID());
	}

	@Test
	public void givenUserExistsFindSailorsWithFirstNameShouldReturnAllUsersWithFirstnameMatching() {
		String firstName = "MatchFN";
		// creating three test users with same firstname
		testDataHelper.createTestSailor(firstName, "One_LN");
		testDataHelper.createTestSailor(firstName, "Two_LN");
		AccountData accountData = new AccountData();
		accountData.firstName(firstName);
		List<Sailor> listOfSailors = sailorAssembly.findSailors(accountData);
		assertThat(listOfSailors, hasSize(2));
		for (Sailor sailor : listOfSailors) {
			assertThat(sailor.firstName(), equalTo(accountData.firstName()));
			testDataHelper.deleteSailor(sailor.id());
		}
	}

	@Test
	public void givenUserNotExistsWithFirstnameMatchingFindReturnsEmptyList() {
		AccountData accountData = new AccountData();
		accountData.firstName(testDataHelper.getInvalidSailorFirstName());
		List<Sailor> listOfSailors = sailorAssembly.findSailors(accountData);
		assertThat(listOfSailors, hasSize(0));
	}

	@Test
	public void givenUserExistsFindSailorsWithDOBReturnsAllUsersWithDOBMatching() {
		String firstName = "MatchFN";
		LocalDate dob = new LocalDate("1900-01-01");

		// creating four test users with same dob
		testDataHelper.createTestSailor(firstName, "MatchLN_1", dob);
		testDataHelper.createTestSailor(firstName, "MatchLN_2", dob);
		testDataHelper.createTestSailor(firstName, "MatchLN_3", dob);
		testDataHelper.createTestSailor(firstName, "MatchLN_4", dob);

		AccountData accountData = new AccountData();
		accountData.dateofBirth(dob);
		List<Sailor> listOfSailors = sailorAssembly.findSailors(accountData);
		assertThat(listOfSailors, hasSize(4));
		for (Sailor sailor : listOfSailors) {
			assertThat(sailor.dateofBirth(), equalTo(accountData.dateofBirth()));
			testDataHelper.deleteSailor(sailor.id());
		}

	}

	@Test
	public void givenUserExistsFindReturnsAllUsersWithFirstnameAndLastNameMatching() {
		String firstName = "MatchFN";
		String lastName = "MatchLN";

		// creating two test users with same firstname and lastname
		testDataHelper.createTestSailor(firstName, lastName, new LocalDate("2017-08-20"));
		testDataHelper.createTestSailor(firstName, lastName, new LocalDate("2017-08-21"));

		AccountData accountData = new AccountData();
		accountData.firstName(firstName);
		accountData.lastName(lastName);
		List<Sailor> listOfSailors = sailorAssembly.findSailors(accountData);
		assertThat(listOfSailors, hasSize(2));
		for (Sailor sailor : listOfSailors) {
			assertThat(sailor.firstName(), equalTo(accountData.firstName()));
			assertThat(sailor.lastName(), equalTo(accountData.lastName()));
			testDataHelper.deleteSailor(sailor.id());
		}

	}

	@Test
	public void givenUserNotExistsWithFirstnameAndLastNameMatchingFindReturnsEmptyList() {
		AccountData accountData = new AccountData();
		accountData.firstName(testDataHelper.getInvalidSailorFirstName());
		accountData.lastName(testDataHelper.getInvalidSailorLastName());
		List<Sailor> listOfSailors = sailorAssembly.findSailors(accountData);
		assertThat(listOfSailors, hasSize(0));
	}

	@Test
	public void givenUserExistsFindReturnsAllUsersWithAllParamsMatching() {

		Sailor testSailor = testDataHelper.createTestSailor();

		AccountData accountData = new AccountData();
		accountData.firstName(testSailor.firstName());
		accountData.lastName(testSailor.lastName());
		accountData.dateofBirth(testSailor.dateofBirth());
		accountData.primaryEmail(testSailor.primaryEmail());
		accountData.mobileNumber(testSailor.mobileNumber());

		List<Sailor> listOfSailors = sailorAssembly.findSailors(accountData);

		assertThat(listOfSailors, hasSize(1));

		for (Sailor sailor : listOfSailors) {
			assertThat(sailor.firstName(), equalTo(accountData.firstName()));
			assertThat(sailor.lastName(), equalTo(accountData.lastName()));
			assertThat(sailor.dateofBirth(), equalTo(accountData.dateofBirth()));
			assertThat(sailor.primaryEmail(), equalTo(accountData.primaryEmail()));
			assertThat(sailor.mobileNumber(), equalTo(accountData.mobileNumber()));
			testDataHelper.deleteSailor(sailor.id());
		}
	}

	@Test
	public void createSailorShouldCreateSailorAndReturnSailor() {

		AccountData accountData = testDataHelper.generateAccountDataToCreate();

		Sailor sailor = sailorAssembly.createSailor(accountData);

		assertThat(accountData.firstName(), equalTo(sailor.firstName()));
		assertThat(accountData.lastName(), equalTo(sailor.lastName()));
		assertThat(accountData.dateofBirth(), equalTo(sailor.dateofBirth()));
		assertThat(accountData.primaryEmail(), equalTo(sailor.primaryEmail()));
		assertThat(accountData.mobileNumber(), equalTo(sailor.mobileNumber()));
		assertThat(testDataHelper.getRecordTypeIdForSailor(), equalTo(sailor.recordTypeId()));

		testDataHelper.deleteSailor(sailor.id());
		
	}
	
	//Orchestration tests
	
	/* Tests for getOrchestratedSailorData
	@Test
	public void givenSeawareClientIDExistsForSailorGetSailorOrchestrationDataShouldReturnFirstAndLastNameInSeaware() {
		
	}
	
	@Test
	public void givenSeawareClientIDDoesNotExistForSailorGetSailorOrchestrationDataShouldReturnFirstAndLastNameInSalesforce() {
		
	} */
	
	/* Tests for getSeawareClientIDForSalesforceID
	@Test
	public void givenSeawareClientIDExistsForSailorIDGetSeawareClientIDForSalesforceIDShouldReturnSeawareClientID() {
		
	}
	
	@Test
	public void givenSeawareClientIDDoesNotExistForSailorIDGetSeawareClientIDForSalesforceIDShouldReturnNull() {
		
	} */
	
	/*  Test for getReferenceTypeIDForName
	@Test
	public void givenReferenceTypeExistsInDBGetReferenceTypeIDForNameShouldReturnID() {
		
	}
	
	@Test
	public void givenReferenceTypeDoesNotExistInDBGetReferenceTypeIDForNameShouldNull() {
		
	} */
	
	/* Tests for getTargetRecordID
	@Test
	public void givenReferenceExistsForParametersGetTargetRecordIDShouldReturnNativeSourceIDValueOfReference() {
		
	}
	
	@Test
	public void givenReferenceDoesNotExistForParametersGetTargetRecordIDShouldReturnNull() {
		
	} */
	
	/* Tests for getSalesforceAccountData
	@Test
	public void givenValidSalesforceIDGetSalesforceAccountDataShouldReturnAccountData() {
		
	}
	
	@Test
	public void givenInvalidSalesforceIDGetSalesforceAccountDataShouldReturnNull() {
		
	} */
	
	/* Tests for getSeawareClientData
	@Test
	public void givenValidSeawareClientIDGetSeawareClientDataShouldReturnClientData() {
		
	}
	
	@Test
	public void givenValidSeawareClientIDGetSeawareClientDataShouldReturnNull() {
		
	}*/

}
