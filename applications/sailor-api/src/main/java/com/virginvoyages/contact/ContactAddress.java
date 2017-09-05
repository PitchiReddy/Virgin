package com.virginvoyages.contact;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true, chain = true)
@EqualsAndHashCode(callSuper = true)
public class ContactAddress extends ContactMethod {

    @JsonProperty("addressLine1")
    private String addressLine1 = null;
    @JsonProperty("addressLine2")
    private String addressLine2 = null;
    @JsonProperty("addressLine3")
    private String addressLine3 = null;
    @JsonProperty("addressLine4")
    private String addressLine4 = null;
    @JsonProperty("city")
    private String city = null;
    @JsonProperty("state")
    private String state = null;
    @JsonProperty("province")
    private String province = null;
    @JsonProperty("country")
    private String country = null;
    @JsonProperty("postalCode")
    private String postalCode = null;
    @JsonProperty("countryCode")
    private String countryCode = null;

    public ContactAddress() {
        super();
        this.contactType("address");
    }

}

