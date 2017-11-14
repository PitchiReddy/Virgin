package com.virginvoyages.assembly.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.virginvoyages.assembly.BookingAssembly;
import com.virginvoyages.booking.BookingsEmbedded;
import com.virginvoyages.crm.data.BookingData;
import com.virginvoyages.crm.data.QueryResultsData;
import com.virginvoyages.dao.BookingsDAO;

import lombok.extern.slf4j.Slf4j;

/**
 * Implementation for {@link BooikngAssemblyImpl}
 *
 * @author pbovilla
 *
 */
@Service
@Slf4j
public class BooikngAssemblyImpl implements BookingAssembly {
	
	@Autowired
	private BookingsDAO bookingsDAO;

	 /**
     * Fetches sailing history based on sailorID. Dummy data being used as of now - as data source not finalized
     * @param sailorID - input sailor id.
     * @return BookingsEmbedded - Contains booking data 
    */
	@Override
	public BookingsEmbedded getSailingHistory(String sailorID) {
		log.debug("Entering getSailingHistory method in BooikngAssemblyImpl");
		BookingsEmbedded pastBookingsEmbedded  = new BookingsEmbedded();
			QueryResultsData<BookingData> queryResultsData = bookingsDAO.getSailingHistory(sailorID);
			for (BookingData bookingData : queryResultsData.records()) {
				pastBookingsEmbedded.addBookingsItem(bookingData.convertToBookingObject());
			}
		return pastBookingsEmbedded;
	}

}
