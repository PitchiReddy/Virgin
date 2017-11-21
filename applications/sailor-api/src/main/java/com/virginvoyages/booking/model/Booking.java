package com.virginvoyages.booking.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.joda.time.LocalDate;

@Data
@Accessors(fluent = true, chain = true)
public class Booking {
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

}

