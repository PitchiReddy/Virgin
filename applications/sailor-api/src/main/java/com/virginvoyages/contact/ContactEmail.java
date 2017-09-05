package com.virginvoyages.contact;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true, chain = true)
@EqualsAndHashCode(callSuper = true)
public class ContactEmail extends ContactMethod {

    @JsonProperty("email")
    private String email = null;

    public ContactEmail() {
        super();
        this.contactType("email");
    }

}

