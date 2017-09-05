package com.virginvoyages.identification;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.joda.time.LocalDate;

@Data
@Accessors(fluent = true, chain = true)
public class Identification {
    @JsonProperty("documentType")
    private String documentType = null;

    @JsonProperty("number")
    private String number = null;

    @JsonProperty("issueCountry")
    private String issueCountry = null;

    @JsonProperty("issueDate")
    private LocalDate issueDate = null;

    @JsonProperty("issueAuthority")
    private String issueAuthority = null;

    @JsonProperty("expiryDate")
    private LocalDate expiryDate = null;

    @JsonProperty("motherMaidenName")
    private String motherMaidenName = null;

    @JsonProperty("fatherName")
    private String fatherName = null;

    @JsonProperty("issueState")
    private String issueState = null;

    @JsonProperty("gender")
    private String gender = null;

    @JsonProperty("issueCity")
    private String issueCity = null;

    @JsonProperty("birthDate")
    private LocalDate birthDate = null;

    @JsonProperty("birthCountryCode")
    private String birthCountryCode = null;

    @JsonProperty("documentAddress")
    private String documentAddress = null;

    @JsonProperty("documentSubType")
    private String documentSubType = null;

}

