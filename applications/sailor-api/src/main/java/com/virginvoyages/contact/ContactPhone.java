package com.virginvoyages.contact;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true, chain = true)
@EqualsAndHashCode(callSuper = true)
public class ContactPhone extends ContactMethod {
    @JsonProperty("phoneNumber")
    private String phoneNumber = null;

    @JsonProperty("extenstion")
    private String extenstion = null;

    public ContactPhone() {
        super();
        this.contactType("phone");
    }
}

