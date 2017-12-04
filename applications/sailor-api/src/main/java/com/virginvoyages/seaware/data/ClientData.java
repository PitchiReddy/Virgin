package com.virginvoyages.seaware.data;

import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.joda.time.LocalDate;

import com.virginvoyages.booking.model.Booking;
import com.virginvoyages.sailor.model.SailorLinks;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true, chain = true)
public class ClientData {
	
	private String id;
    private String firstName;
	private String middleName;
	private String lastName;
	private String suffix;
	private String prefix;
	private String salutation;
	private String preferredName;
	private String gender;
	private String occupation;
	private String citizenshipCountry;
	private String preferredLanguage;
    private XMLGregorianCalendar dateofBirth;
    private String birthCountry;
    private String ageGroup;
    private String alternateName;
    private LocalDate retirementDate;
    private String martialStatus;
    private Integer numberofChildren;
    private String totalLifetimeCruiseNights;
    private String tribe;
    private String subTribe;
    private String stateOfTheSailor;
    private String status;
    private String vIP;
    private List<Booking> sailingHistory = new ArrayList<Booking>();
    private String primaryEmail;
    private String mobileNumber;
    private SailorLinks links = null;
   
}
