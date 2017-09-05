package com.virginvoyages.identification;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(fluent = true, chain = true)
public class IdentificationsEmbedded {
    @JsonProperty("identifications")
    private List<Identification> identifications = new ArrayList<Identification>();

    public IdentificationsEmbedded addIdentificationsItem(Identification identificationItem) {
        this.identifications.add(identificationItem);
        return this;
    }
}

