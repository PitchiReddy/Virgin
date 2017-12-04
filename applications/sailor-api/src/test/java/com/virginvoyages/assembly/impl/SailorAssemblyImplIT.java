package com.virginvoyages.assembly.impl;

import static com.virginvoyages.crossreference.constants.CrossReferenceConstants.REFERENCE_TYPE_CLIENT;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.IsNull.notNullValue;

import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.virginvoyages.assembly.SailorAssembly;
import com.virginvoyages.exception.DataNotFoundException;
import com.virginvoyages.sailor.helper.TestDataHelper;
import com.virginvoyages.sailor.model.Sailor;
import com.virginvoyages.seaware.data.ClientData;

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
	private SailorAssemblyImpl sailorAssemblyImpl;

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
		Sailor sailorData = new Sailor();
		sailorData.firstName(firstName);
		List<Sailor> listOfSailors = sailorAssembly.findSailors(sailorData);
		assertThat(listOfSailors, hasSize(2));
		for (Sailor sailor : listOfSailors) {
			assertThat(sailor.firstName(), equalTo(sailorData.firstName()));
			testDataHelper.deleteSailor(sailor.id());
		}
	}

	@Test
	public void givenUserNotExistsWithFirstnameMatchingFindReturnsEmptyList() {
		Sailor sailorData = new Sailor();
		sailorData.firstName(testDataHelper.getInvalidSailorFirstName());
		List<Sailor> listOfSailors = sailorAssembly.findSailors(sailorData);
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

		Sailor sailorData = new Sailor();
		sailorData.dateofBirth(dob);
		List<Sailor> listOfSailors = sailorAssembly.findSailors(sailorData);
		assertThat(listOfSailors, hasSize(4));
		for (Sailor sailor : listOfSailors) {
			assertThat(sailor.dateofBirth(), equalTo(sailorData.dateofBirth()));
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

		Sailor sailorData = new Sailor();
		sailorData.firstName(firstName);
		sailorData.lastName(lastName);
		List<Sailor> listOfSailors = sailorAssembly.findSailors(sailorData);
		assertThat(listOfSailors, hasSize(2));
		for (Sailor sailor : listOfSailors) {
			assertThat(sailor.firstName(), equalTo(sailorData.firstName()));
			assertThat(sailor.lastName(), equalTo(sailorData.lastName()));
			testDataHelper.deleteSailor(sailor.id());
		}

	}

	@Test
	public void givenUserNotExistsWithFirstnameAndLastNameMatchingFindReturnsEmptyList() {
		Sailor sailorData = new Sailor();
		sailorData.firstName(testDataHelper.getInvalidSailorFirstName());
		sailorData.lastName(testDataHelper.getInvalidSailorLastName());
		List<Sailor> listOfSailors = sailorAssembly.findSailors(sailorData);
		assertThat(listOfSailors, hasSize(0));
	}

	@Test
	public void givenUserExistsFindReturnsAllUsersWithAllParamsMatching() {

		Sailor testSailor = testDataHelper.createTestSailor();

		Sailor sailorData = new Sailor();
		sailorData.firstName(testSailor.firstName());
		sailorData.lastName(testSailor.lastName());
		sailorData.dateofBirth(testSailor.dateofBirth());
		sailorData.primaryEmail(testSailor.primaryEmail());
		sailorData.mobileNumber(testSailor.mobileNumber());

		List<Sailor> listOfSailors = sailorAssembly.findSailors(sailorData);

		assertThat(listOfSailors, hasSize(1));

		for (Sailor sailor : listOfSailors) {
			assertThat(sailor.firstName(), equalTo(sailorData.firstName()));
			assertThat(sailor.lastName(), equalTo(sailorData.lastName()));
			assertThat(sailor.dateofBirth(), equalTo(sailorData.dateofBirth()));
			assertThat(sailor.primaryEmail(), equalTo(sailorData.primaryEmail()));
			assertThat(sailor.mobileNumber(), equalTo(sailorData.mobileNumber()));
			testDataHelper.deleteSailor(sailor.id());
		}
	}

	@Test
	public void createSailorShouldCreateSailorAndReturnSailor() {

		Sailor testSailor = testDataHelper.createTestSailor();

		Sailor sailor = sailorAssembly.createSailor(testSailor);

		assertThat(testSailor.firstName(), equalTo(sailor.firstName()));
		assertThat(testSailor.lastName(), equalTo(sailor.lastName()));
		assertThat(testSailor.dateofBirth(), equalTo(sailor.dateofBirth()));
		assertThat(testSailor.primaryEmail(), equalTo(sailor.primaryEmail()));
		assertThat(testSailor.mobileNumber(), equalTo(sailor.mobileNumber()));
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
	
	//  Test for getReferenceTypeIDForName
	@Test
	public void givenReferenceTypeExistsInDBGetReferenceTypeIDForNameShouldReturnID() {
		assertThat(sailorAssembly.getReferenceTypeIDForName(REFERENCE_TYPE_CLIENT), notNullValue());
	}
	
	@Test 
	public void givenReferenceTypeDoesNotExistInDBGetReferenceTypeIDForNameShouldNull() {
		assertThat(sailorAssembly.getReferenceTypeIDForName("random"), nullValue());
	}
	
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
	
	//Tests for getSeawareClientData
	@Test
	public void givenValidSeawareClientIDGetSeawareClientDataShouldReturnClientData() {
		String clientID = testDataHelper.getValidSeawareClientID();
		ClientData clientData = sailorAssemblyImpl.getSeawareClientData(clientID);
		assertThat(clientID, equalTo(clientData.id()));
	}
	
	@Test
	public void givenValidSeawareClientIDGetSeawareClientDataShouldReturnNull() {
		assertThat(sailorAssemblyImpl.getSeawareClientData("dummy"), nullValue());
	}

}
