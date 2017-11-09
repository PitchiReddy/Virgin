package com.virginvoyages.sailor.helper;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.virginvoyages.assembly.SailorAssembly;
import com.virginvoyages.crm.client.QueryClient;
import com.virginvoyages.crm.data.AccountData;
import com.virginvoyages.crm.data.BookingChannel;
import com.virginvoyages.crm.data.OTA_ReadRQ;
import com.virginvoyages.crm.data.POS;
import com.virginvoyages.crm.data.ProfileReadRequest;
import com.virginvoyages.crm.data.ReadRequests;
import com.virginvoyages.crm.data.RequestorID;
import com.virginvoyages.crm.data.SeawareData;
import com.virginvoyages.crm.data.Source;
import com.virginvoyages.crm.data.UniqueID;
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
	

	public OTA_ReadRQ genarateSeawaredataToCreate() {
		SeawareData seawareData = new SeawareData();
		
		OTA_ReadRQ oTA_ReadRQ = new OTA_ReadRQ();
		POS pos = new POS();
		Source source = new Source();
		ReadRequests readRequests = new ReadRequests();
		ProfileReadRequest profileReadRequest =  new ProfileReadRequest();
		UniqueID uniqueID = new UniqueID();
		uniqueID.setID("405");
		uniqueID.setID_Context("SEAWARE");
		uniqueID.setType("1");
		profileReadRequest.setUniqueID(uniqueID);
		readRequests.setProfileReadRequest(profileReadRequest);
		RequestorID requestorID = new RequestorID();
		requestorID.setID("5");
		requestorID.setType("5");
		requestorID.setID_Context("SEAWARE");
		BookingChannel bookingChannel = new BookingChannel();
		bookingChannel.setCompanyName("OPENTRAVEL");
		bookingChannel.setType("1");
		
		
		
		source.setRequestorID(requestorID);
		
		source.setBookingChannel(bookingChannel);
		oTA_ReadRQ.setPOS(pos);
		oTA_ReadRQ.setReadRequests(new ReadRequests());
		oTA_ReadRQ.setPrimaryLangID("ENG");
		oTA_ReadRQ.setVersion("1");
		oTA_ReadRQ.setXmlns("http://www.opentravel.org/OTA/2003/05");
		oTA_ReadRQ.setReadRequests(readRequests);
		return oTA_ReadRQ;
	}
}
