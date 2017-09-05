package com.virginvoyages.booking;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.virginvoyages.model.Links;
import com.virginvoyages.model.Page;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true, chain = true)
public class Bookings {
    @JsonProperty("_links")
    private Links links = null;

    @JsonProperty("page")
    private Page page = null;

    @JsonProperty("_embedded")
    private BookingsEmbedded embedded = null;
}

