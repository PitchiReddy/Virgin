package com.virginvoyages.contact.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import lombok.Data;
import lombok.experimental.Accessors;
import org.joda.time.LocalDate;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "contactType", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ContactPhone.class, name = "ContactPhone"),
        @JsonSubTypes.Type(value = ContactAddress.class, name = "ContactAddress"),
        @JsonSubTypes.Type(value = ContactEmail.class, name = "ContactEmail"),
})
@Data
@Accessors(fluent = true, chain = true)
public class ContactMethod {
    @JsonProperty("contactType")
    private String contactType = null;

    @JsonProperty("contactSubType")
    private String contactSubType = null;

    @JsonProperty("primary")
    private String primary = null;

    @JsonProperty("activeDateFrom")
    private LocalDate activeDateFrom = null;

    @JsonProperty("activeDateTo")
    private LocalDate activeDateTo = null;

    @JsonProperty("deliverableStatus")
    private String deliverableStatus = null;

    @JsonProperty("undeliverableStatus")
    private String undeliverableStatus = null;

    @JsonProperty("capturedDate")
    private LocalDate capturedDate = null;

}

