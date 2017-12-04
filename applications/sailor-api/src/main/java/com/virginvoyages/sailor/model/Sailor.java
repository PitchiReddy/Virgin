package com.virginvoyages.sailor.model;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.hateoas.Identifiable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.virginvoyages.booking.model.Booking;
import com.virginvoyages.booking.model.BookingsEmbedded;
import com.virginvoyages.preference.model.Preference;
import com.virginvoyages.preference.model.PreferencesEmbedded;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true, chain = true)
public class Sailor implements Identifiable<String> {

    @JsonProperty("id")
    private String id;

    @JsonProperty("seawareClientID")
    private String seawareClientID;

    @JsonProperty("prefix")
    private String prefix;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("middleName")
    private String middleName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("suffix")
    private String suffix;

    @JsonProperty("preferredName")
    private String preferredName;

    @JsonProperty("nickName")
    private String nickName;

    @JsonProperty("gender")
    private String gender;

    @JsonProperty("occupation")
    private String occupation;

    @JsonProperty("citizenshipCountry")
    private String citizenshipCountry;

    @JsonProperty("preferredLanguage")
    private String preferredLanguage;

    @JsonProperty("dateofBirth")
    private LocalDate dateofBirth;

    @JsonProperty("birthCountry")
    private String birthCountry;

    @JsonProperty("ageGroup")
    private String ageGroup;

    @JsonProperty("anniversaryDate")
    private LocalDate anniversaryDate;

    @JsonProperty("retirementDate")
    private LocalDate retirementDate;

    @JsonProperty("martialStatus")
    private String martialStatus;

    @JsonProperty("numberofChildren")
    private Integer numberofChildren;

    @JsonProperty("tribe")
    private String tribe;
    
    @JsonProperty("subTribe")
    public String subTribe;
    
    @JsonProperty("stateOfTheSailor")
    public String stateOfTheSailor;

    @JsonProperty("status")
    private String status;

    @JsonProperty("vIP")
    private String vIP;

    @JsonProperty("averageNTRAmount")
    private String averageNTRAmount;

    @JsonProperty("averageOBSAmount")
    private String averageOBSAmount;

    @JsonProperty("totalLifetimeRevenuePerDiem")
    private String totalLifetimeRevenuePerDiem;

    @JsonProperty("totalLifetimePerDiem")
    private String totalLifetimePerDiem;

    @JsonProperty("totalLifetimeCruiseNights")
    private String totalLifetimeCruiseNights;

    @JsonProperty("salutation")
    private String salutation;
    
    @JsonProperty("email")
    private String primaryEmail;
    
    @JsonProperty("mobileNumber")
    private String mobileNumber;
    
    @JsonProperty("recordTypeId")
    private String recordTypeId;

    @JsonProperty("preferences")
    private List<Preference> preferences = new ArrayList<Preference>();

    @JsonProperty("sailingHistory")
    private List<Booking> sailingHistory = new ArrayList<Booking>();

    @JsonProperty("_links")
    private SailorLinks links = null;

    @Override
    public String getId() {
        return id();
    }
    
    public Sailor associatePreferences(PreferencesEmbedded preferencesEmbedded) {    	
    	return preferencesEmbedded == null || preferencesEmbedded.preferences().isEmpty() ? this : this.preferences(preferencesEmbedded.preferences());
    }
    
    public Sailor associateSailingHistory(BookingsEmbedded bookingsEmbedded) {
    	return bookingsEmbedded == null ? this : this.sailingHistory(bookingsEmbedded.bookings());
    }
}

