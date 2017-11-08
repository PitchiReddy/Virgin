package com.virginvoyages.sailor.helper;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.virginvoyages.assembly.SailorAssembly;
import com.virginvoyages.crm.client.QueryClient;
import com.virginvoyages.crm.data.AccountData;
import com.virginvoyages.crm.data.ReferenceData;
import com.virginvoyages.sailor.Sailor;

@Service
public class TestDataHelper {

	@Autowired
	private SailorAssembly sailorAssembly;
	
	@Autowired	
	private QueryClient queryClient;
	
	public Sailor createTestSailor() {
		return createTestSailor("Sapi_TestFN", "Sapi_TestLN");
	}

	public Sailor createTestSailor(String firstName, String lastName) {
		return createTestSailor(firstName, lastName, LocalDate.now());
	}

	public Sailor createTestSailor(String firstName, String lastName, LocalDate dob) {
		AccountData accountData = new AccountData();
		accountData.firstName(firstName);
		accountData.lastName(lastName);
		accountData.dateofBirth(dob);
		accountData.primaryEmail("sapi@test.com");
		accountData.mobileNumber("1234567890");
		return sailorAssembly.createSailor(accountData);
	}

	public void deleteSailor(String sailorID) {
		sailorAssembly.deleteSailorById(sailorID);
	}
	
	public String getValidSailorId () {
		return createTestSailor().id();
	}
	
	public String getRecordTypeIdForSailor() {
		return queryClient.findSailorAccountRecordTypeID().first().id();
	}
	


	public String getInvalidSailorID() {
		return "invalidid";
	}

	public String getInvalidSailorFirstName() {
		return "nofirstname";
	}

	public String getInvalidSailorLastName() {
		return "nolastname";
	}

	public AccountData generateAccountDataToCreate() {
		AccountData accountData = new AccountData();
		accountData.firstName("Sapi_TestFN");
		accountData.lastName("Sapi_TestLN");
		accountData.primaryEmail("sapi@test.com");
		accountData.dateofBirth(LocalDate.now());
		accountData.mobileNumber("1234567890");
		return accountData;
	}

	public String getSailorIDWithPreferences() {
		return "0010n00000EqDqfAAF";

	}
		
	public String getSailorIDWithoutPreferences() {
		return "0010n00000EP8OyAAL";
    }
	
	public String getSailorIDWithContactMethods() {
		return "TBD";
	}
	
	public String getSailorIDWithoutContactMethods() {
		return "TBD";
	}
	
	public String getSailorIDWithSailingHistory() {
		return "1234";
	}
	
	public String getSailorIDWithOutSailingHistory() {
		return " ";
	}
	
	public ReferenceData generateReferenceSource() {
		ReferenceData referenceData = new ReferenceData();
		referenceData.masterID("M1");
		referenceData.nativeSourceIDValue("NSID1");
		referenceData.referenceTypeID("8acdcfb55f9185fa015f918615c20004");
		System.out.println("ReferenceData    " + referenceData);
		return referenceData;
	}
}
