package com.virginvoyages.crm.data;

import org.joda.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.virginvoyages.booking.Booking;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true, chain = true)
public class BookingData {
	
	@JsonProperty("reservationNumber")
    private String reservationNumber;

    @JsonProperty("status")
    private String status;

    @JsonProperty("sailingPackage")
    private String sailingPackage;

    @JsonProperty("ship")
    private String ship;

    @JsonProperty("daysonSailing")
    private Integer daysonSailing;

    @JsonProperty("sailDate")
    private LocalDate sailDate;

    @JsonProperty("embarkationDate")
    private LocalDate embarkationDate;

    @JsonProperty("disembarkationDate")
    private LocalDate disembarkationDate;

    @JsonProperty("prepaidGratuities")
    private String prepaidGratuities;

    @JsonProperty("vacationProtection")
    private String vacationProtection;

    @JsonProperty("preCruiseTransfer")
    private String preCruiseTransfer;

    @JsonProperty("postCruiseTransfer")
    private String postCruiseTransfer;

    @JsonProperty("flightPurchase")
    private String flightPurchase;

    @JsonProperty("brand")
    private String brand;

    @JsonProperty("onboardSpend")
    private String onboardSpend;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("bookedBySailor")
    private String bookedBySailor;

    @JsonProperty("dateBooked")
    private LocalDate dateBooked;

    @JsonProperty("numberofGuests")
    private Integer numberofGuests;

    @JsonProperty("numberofStateroom")
    private Integer numberofStateroom;

    @JsonProperty("numberofAcessibleStaterooms")
    private Integer numberofAcessibleStaterooms;

	public Booking convertToBookingObject() {
		Booking booking = new Booking();
		
		booking.bookedBySailor(this.bookedBySailor);
		booking.brand(this.brand);
		booking.sailDate(this.sailDate);
		booking.dateBooked(this.dateBooked);
		booking.reservationNumber(this.reservationNumber);
		booking.currency(this.currency);
		booking.numberofAcessibleStaterooms(this.numberofAcessibleStaterooms);
		booking.numberofGuests(this.numberofGuests);
		booking.daysonSailing(this.daysonSailing);
		booking.disembarkationDate(this.disembarkationDate);
		booking.onboardSpend(this.onboardSpend);
		booking.embarkationDate(this.embarkationDate);
		booking.prepaidGratuities(this.prepaidGratuities);
		booking.vacationProtection(this.vacationProtection);
		booking.preCruiseTransfer(this.preCruiseTransfer);
		booking.postCruiseTransfer(this.postCruiseTransfer);
		booking.flightPurchase(this.flightPurchase);
		booking.numberofStateroom(this.numberofStateroom);
		booking.ship(this.ship);
		booking.status(this.status);
		booking.sailingPackage(this.sailingPackage);
		return booking;
		
	}

}
