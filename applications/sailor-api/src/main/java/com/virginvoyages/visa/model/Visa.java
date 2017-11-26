package com.virginvoyages.visa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.joda.time.LocalDate;

@Data
@Accessors(fluent = true, chain = true)
public class Visa {
    @JsonProperty("visaType")
    private String visaType = null;

    @JsonProperty("issueCountry")
    private String issueCountry = null;

    @JsonProperty("number")
    private String number = null;

    @JsonProperty("issueDate")
    private LocalDate issueDate = null;

    @JsonProperty("expiryDate")
    private LocalDate expiryDate = null;

    @JsonProperty("numberofEntries")
    private Integer numberofEntries = null;

}

