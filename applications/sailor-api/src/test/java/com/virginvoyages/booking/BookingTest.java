package com.virginvoyages.booking;

import org.junit.Test;

import com.virginvoyages.booking.model.Booking;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;


public class BookingTest {

    @Test
    public void toStringBookingShouldContainAttributes() {
        String brand = "Virgin";
        Booking booking = new Booking().brand(brand);
        assertThat(booking.toString(), containsString(brand));
    }
}
