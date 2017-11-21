package com.virginvoyages.sailor.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.hateoas.Link;

@Data
@Accessors(fluent = true, chain = true)
public class SailorLinks {
    public static final String BOOKINGS = "bookings";
    public static final String HOUSEHOLDS = "households";
    public static final String CONTACTMETHODS = "contactmethods";
    public static final String LOYALTIES = "loyalties";
    public static final String VISAS = "visas";
    public static final String IDENTIFICATIONS = "identifications";
    public static final String WEARABLES = "wearables";

    @JsonProperty("self")
    private Link self;

    @JsonProperty(BOOKINGS)
    private Link bookings;

    @JsonProperty(HOUSEHOLDS)
    private Link households;

    @JsonProperty(CONTACTMETHODS)
    private Link contactmethods;

    @JsonProperty(LOYALTIES)
    private Link loyalties;

    @JsonProperty(VISAS)
    private Link visas;

    @JsonProperty(IDENTIFICATIONS)
    private Link identifications;

    @JsonProperty(WEARABLES)
    private Link wearables;

}

