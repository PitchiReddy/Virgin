package com.virginvoyages.crm.data;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.virginvoyages.booking.model.Booking;
import com.virginvoyages.preference.model.Preference;
import com.virginvoyages.sailor.helper.SailorMapper;
import com.virginvoyages.sailor.model.Sailor;
import com.virginvoyages.sailor.model.SailorLinks;

import lombok.Data;
import lombok.experimental.Accessors;

/**
* AccountData class contains all the properties of Account
* Used lombok annotations for generating setters, getters,toString(),hashCode() and etc..
* @author snarthu
*
+*/

@Data
@Accessors(fluent = true, chain = true)
public class AccountData {
	
	@JsonProperty("attributes")
	private Attributes attributes;

    @JsonProperty("Id")
    private String id;
    
	@JsonProperty("FirstName")
	private String firstName;
	
	@JsonProperty("MiddleName")
	private String middleName;

	@JsonProperty("LastName")
	private String lastName;

	@JsonProperty("Suffix")
	private String suffix;

	@JsonProperty("Salutation")
	private String salutation;

	@JsonProperty("Preferred_Name__c")
	private String preferredName;

	@JsonProperty("Gender__pc")
	private String gender;

	@JsonProperty("Occupation__c")
	private String occupation;

	@JsonProperty("Citizenship_Country__c")
	private String citizenshipCountry;

	@JsonProperty("PreferredLanguage")
	private String preferredLanguage;
	
	@JsonProperty("PersonBirthdate")
    private LocalDate dateofBirth;

    @JsonProperty("Birth_Country__c")
    private String birthCountry;

    @JsonProperty("AgeGroup")
    private String ageGroup;

    @JsonProperty("AlternateName")
    private String alternateName;
    
    @JsonProperty("RetirementDate")
    private LocalDate retirementDate;

    @JsonProperty("Marital_Status__pc")
    private String martialStatus;

    @JsonProperty("children__pc")
    private Integer numberofChildren;

    @JsonProperty("Total_Lifetime_Cruise_Nights__c")
    private String totalLifetimeCruiseNights;

    @JsonProperty("Tribe__pc")
    private String tribe;
    
    @JsonProperty("Subtribe__c")
    private String subTribe;
    
    @JsonProperty("State_of_the_Sailor__c")
    private String stateOfTheSailor;
    
    @JsonProperty("Status__pc")
    private String status;

    @JsonProperty("VIP__c")
    private String vIP;
    
    @JsonProperty("preferences")
    private List<Preference> preferences = new ArrayList<Preference>();

    @JsonProperty("sailingHistory")
    private List<Booking> sailingHistory = new ArrayList<Booking>();
    
    @JsonProperty("Primary_Contact_Email__c")
    private String primaryEmail;
    
    @JsonProperty("PersonMobilePhone")
    private String mobileNumber;
    
    @JsonProperty("_links")
    private SailorLinks links = null;
    
    @JsonProperty("RecordTypeId")
    private String recordTypeId = null;
    
    public Sailor convertToSailorObject() {
    	return SailorMapper.mapAccountDataToSailor(this);
    }
}
