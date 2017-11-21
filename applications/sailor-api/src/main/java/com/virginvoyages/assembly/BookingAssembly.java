package com.virginvoyages.assembly;

import com.virginvoyages.booking.model.BookingsEmbedded;

/**
 * {@code Interface} for assembly tasks for find sailor bookings
 * @author pbovilla
 *
 */
public interface BookingAssembly {


	/**
	 * Find sailing history based on sailorID
	 * @param sailorID 
	 * @return BookingsEmbedded
	 */
	public BookingsEmbedded getSailingHistory(String sailorID);

}
