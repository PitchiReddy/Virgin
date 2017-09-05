package com.virginvoyages.booking;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(fluent = true, chain = true)
public class BookingsEmbedded {
    @JsonProperty("bookings")
    private List<Booking> bookings = new ArrayList<Booking>();

    public BookingsEmbedded addBookingsItem(Booking bookingsItem) {
        this.bookings.add(bookingsItem);
        return this;
    }
}

