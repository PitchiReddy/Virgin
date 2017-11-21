package com.virginvoyages.contact.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(fluent = true, chain = true)
public class ContactMethodsEmbedded {
    @JsonProperty("contactMethods")
    private List<ContactMethod> contactMethods = new ArrayList<ContactMethod>();

    public ContactMethodsEmbedded addContactMethod(ContactMethod contactMethod) {
        this.contactMethods.add(contactMethod);
        return this;
    }
}
