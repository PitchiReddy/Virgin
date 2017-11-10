package com.virginvoyages.sailor.helper;

import java.math.BigDecimal;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.virginvoyages.assembly.SailorAssembly;
import com.virginvoyages.crm.client.QueryClient;
import com.virginvoyages.crm.data.AccountData;
import com.virginvoyages.sailor.Sailor;
import com.virginvoyages.seaware.data.CompanyNameType;
import com.virginvoyages.seaware.data.OTAReadRQ;
import com.virginvoyages.seaware.data.POSType;
import com.virginvoyages.seaware.data.SourceType;

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
	

	public OTAReadRQ genarateSeawaredataToCreate() {
		
		final OTAReadRQ otaReadRQ = new OTAReadRQ();
		POSType posType = new POSType();
	    SourceType sourceType = new SourceType();
	    OTAReadRQ.ReadRequests readRequests = new OTAReadRQ.ReadRequests();
	    OTAReadRQ.ReadRequests.ProfileReadRequest profileReadRequest =  new OTAReadRQ.ReadRequests.ProfileReadRequest();
	    OTAReadRQ.ReadRequests.ProfileReadRequest.UniqueID uniqueID = new OTAReadRQ.ReadRequests.ProfileReadRequest.UniqueID();
		SourceType.RequestorID  requestorID = new SourceType.RequestorID();
		SourceType.BookingChannel bookingChannel = new SourceType.BookingChannel();
		CompanyNameType companyNameType = new CompanyNameType();
		companyNameType.setValue("OPENTRAVEL");
	    uniqueID.setID("405");
		uniqueID.setIDContext("SEAWARE");
		uniqueID.setType("1");
		profileReadRequest.getUniqueID().add(uniqueID);
		readRequests.getProfileReadRequest().add(profileReadRequest);
	
		requestorID.setID("5");
		requestorID.setType("5");
		requestorID.setIDContext("SEAWARE");
		
		bookingChannel.setCompanyName(companyNameType);
		bookingChannel.setType("1");
		
		
		
		sourceType.setRequestorID(requestorID);
		
		sourceType.setBookingChannel(bookingChannel);
		posType.getSource().add(sourceType);
		otaReadRQ.setPOS(posType);
		otaReadRQ.setReadRequests(readRequests);
		otaReadRQ.setPrimaryLangID("ENG");
		otaReadRQ.setVersion(new BigDecimal(1));
		//otaReadRQ.setXmlns("http://www.opentravel.org/OTA/2003/05");
		otaReadRQ.setReadRequests(readRequests);
		return otaReadRQ;
	}
}
