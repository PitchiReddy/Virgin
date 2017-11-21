package com.virginvoyages.sailor.helper;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.virginvoyages.booking.model.Booking;
import com.virginvoyages.booking.model.BookingsEmbedded;
import com.virginvoyages.contact.model.ContactMethod;
import com.virginvoyages.contact.model.ContactMethodsEmbedded;
import com.virginvoyages.crm.data.AccountCreateStatus;
import com.virginvoyages.crm.data.AccountData;
import com.virginvoyages.crm.data.BookingData;
import com.virginvoyages.crm.data.ContactMethodsData;
import com.virginvoyages.crm.data.PreferenceData;
import com.virginvoyages.crm.data.QueryResultsData;
import com.virginvoyages.preference.model.Preference;
import com.virginvoyages.preference.model.PreferencesEmbedded;
import com.virginvoyages.sailor.api.MockSailorAPI;
import com.virginvoyages.sailor.model.Sailor;

@Component
public class MockDataHelper {
	
	@Autowired
	private MockSailorAPI mockSailorAPI;
	
	public Sailor getMockSailor() {
		return mockSailorAPI.getSailorById("123");
	}
	
	public QueryResultsData<PreferenceData> getPreferenceDataQueryResultsData(boolean withData){
		QueryResultsData<PreferenceData> queryResultsData = new QueryResultsData<PreferenceData>();
		List<PreferenceData> prefDataList = new ArrayList<PreferenceData>();
		if(withData) {
			PreferenceData preferenceData = new PreferenceData();
			preferenceData.category("category_1");
			preferenceData.subCategory("sub_category_1");
			preferenceData.options("preference_1");
			prefDataList.add(preferenceData);
		}
		queryResultsData.records(prefDataList);
		return queryResultsData;
	}
	
	public QueryResultsData<ContactMethodsData> getContactMethodsDataQueryResultsData(boolean withData){
		
		QueryResultsData<ContactMethodsData> queryResultsData = new QueryResultsData<ContactMethodsData>();
		List<ContactMethodsData> contactMethodsDataList = new ArrayList<ContactMethodsData>();
		if(withData) {
			contactMethodsDataList.add(createContactMethodsData("phone"));
			contactMethodsDataList.add(createContactMethodsData("email"));
			contactMethodsDataList.add(createContactMethodsData("address"));
		}
		
		queryResultsData.records(contactMethodsDataList);
		return queryResultsData;
		
	}
	
	private ContactMethodsData createContactMethodsData(String contactMethodType) {
		ContactMethodsData contactMethodsData = new ContactMethodsData();

		contactMethodsData.addressLine1("Line 1");
		contactMethodsData.addressLine2("Line 2");
		contactMethodsData.addressLine3("Line 3");
		contactMethodsData.addressLine4("Line 4");
		contactMethodsData.city("Plantation");
		contactMethodsData.state("FL");
		contactMethodsData.province("Plant");
		contactMethodsData.countryCode("US");
		contactMethodsData.postalCode("90001");
		contactMethodsData.country("United States");
		contactMethodsData.email("sailor@virgin.com");
		contactMethodsData.phoneNumber("999-888-7777");
		contactMethodsData.extenstion("x1243");
		contactMethodsData.recordTypeId(contactMethodType);
		
		return contactMethodsData;
	}
	
	public PreferencesEmbedded getPreferencesEmbedded(boolean withData){
		PreferencesEmbedded preferencesEmbedded = new PreferencesEmbedded();
		if(withData) {
			Preference preference = new Preference();
			preference.category("category_1");
			preference.subCategory("sub_category_1");
			preference.option("preference_1");
			preferencesEmbedded.addPreferencesItem(preference);
		}
		return preferencesEmbedded;
	}
	
	public QueryResultsData<AccountData> getQueryResultsDataWithAccountData(AccountData accountData){
		List<AccountData> accountList = new ArrayList<AccountData>();
		accountList.add(accountData);
		return new QueryResultsData<AccountData>().records(accountList);
	}
	
	public QueryResultsData<AccountData> getQueryResultsDataWithThreeAccountDataRecordsWithSameData(){
		List<AccountData> accountList = new ArrayList<AccountData>();
		accountList.add(generateAccountDataToCreate());
		accountList.add(generateAccountDataToCreate());
		accountList.add(generateAccountDataToCreate());
		return new QueryResultsData<AccountData>().records(accountList);
	}
	
	public AccountCreateStatus getAccountCreateStatus(String success){
		AccountCreateStatus status = new AccountCreateStatus();
		status.successStatus(success);
		return status;
	}
	

	public ContactMethodsEmbedded getSailorContactMethodsEmbedded(boolean withData){
		ContactMethodsEmbedded contactMethodsEmbedded = new ContactMethodsEmbedded();
		if(withData) {
			ContactMethod contactMethod = new ContactMethod();
			contactMethod.activeDateFrom(LocalDate.now());
			contactMethod.activeDateTo(LocalDate.now());
			contactMethod.capturedDate(LocalDate.now());
			contactMethod.contactSubType("Home");
			contactMethod.contactType("phone");
			contactMethod.deliverableStatus("Delivered");
			contactMethod.primary("Yes");
			contactMethod.undeliverableStatus("NA");
			contactMethodsEmbedded.addContactMethod(contactMethod);
		}
		return contactMethodsEmbedded;
		
	}

	/*public String getInvalidSailorID() {
		return "invalidid";
	}

	public String getInvalidSailorFirstName() {
		return "nofirstname";
	}

	public String getInvalidSailorLastName() {
		return "nolastname";
	}*/

	public AccountData generateAccountDataToCreate() {
		AccountData accountData = new AccountData();
		accountData.firstName("Sapi_TestFN");
		accountData.lastName("Sapi_TestLN");
		accountData.primaryEmail("sapi@test.com");
		accountData.dateofBirth(LocalDate.now());
		accountData.mobileNumber("1234567890");
		return accountData;
	}
	
	public String getSailorId() {
		return "mockid";
		//return mockSailorAPI.sailorsFindGet(null, null, null, "Sailor", null).get(0).id();
	}

/*	public String getSailorIDWithPreferences() {
		return "0010n00000EqDqfAAF";

	}
	
	public String getSailorIDWithoutPreferences() {
		return "0010n00000EP8OyAAL";
    }
	
	public String getValidSailorId () {
		return getSailorIDWithPreferences();
	}*/

	public BookingsEmbedded createSailingHistory(boolean withData) {
		BookingsEmbedded bookingsEmbedded = new BookingsEmbedded();
		if(withData) {
		Booking booking = new Booking();
		booking.bookedBySailor("The Sailor");
		booking.brand("Virgin");
		booking.sailDate(LocalDate.now());
		booking.dateBooked(LocalDate.now());
		booking.reservationNumber("123");
		booking.currency("USD");
		booking.numberofAcessibleStaterooms(0);
		booking.numberofGuests(1);
		booking.daysonSailing(5);
		booking.disembarkationDate(LocalDate.now());
		booking.onboardSpend("100.00");
		booking.embarkationDate(LocalDate.now());
		booking.prepaidGratuities("None");
		booking.vacationProtection("Yes");
		booking.preCruiseTransfer("Air");
		booking.postCruiseTransfer("Hotel");
		booking.flightPurchase("Yes");
		booking.numberofStateroom(1);
		booking.ship("Virgin");
		booking.status("BOOK");
		booking.sailingPackage("All Inclusive");
		bookingsEmbedded.addBookingsItem(booking);
		}
		return bookingsEmbedded;
	}
	
	public BookingData generateSailorBookingDataToCreate() {
		BookingData bookingData = new BookingData();
		bookingData.bookedBySailor("The Sailor");
		bookingData.brand("Virgin");
		bookingData.sailDate(LocalDate.now());
		bookingData.dateBooked(LocalDate.now());
		return bookingData;
	}
	
	public QueryResultsData<BookingData> getSailorBookingQueryResultsData(boolean withData){
		
		QueryResultsData<BookingData> queryResultsData = new QueryResultsData<BookingData>();
		List<BookingData> bookingDataList = new ArrayList<BookingData>();
		if(withData) {
			bookingDataList.add(generateSailorBookingDataToCreate());
		}
		queryResultsData.records(bookingDataList);
		return queryResultsData;
	}

}
