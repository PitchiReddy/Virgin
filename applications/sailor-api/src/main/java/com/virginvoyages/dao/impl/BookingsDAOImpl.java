package com.virginvoyages.dao.impl;

import java.util.ArrayList;
import java.util.List;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Service;
import com.virginvoyages.crm.data.BookingData;
import com.virginvoyages.crm.data.QueryResultsData;
import com.virginvoyages.dao.BookingsDAO;
import lombok.extern.slf4j.Slf4j;

/**
 * Data access layer for sailor history methods.
 * Stubbed out now as data source yet to be finalized
 * @author pbovilla 
 */
@Service
@Slf4j
public class BookingsDAOImpl implements BookingsDAO {

	/**
	 * Mock implementation.
	 */
	@Override
	public QueryResultsData<BookingData> getSailingHistory(String sailorID) {
		log.debug("Entering getSailorBookings method");

		QueryResultsData<BookingData> queryResultsData = new QueryResultsData<BookingData>();
		List<BookingData> bookingDataList = new ArrayList<BookingData>();
		bookingDataList.add(createSailingHistory());
		queryResultsData.records(bookingDataList);
		return queryResultsData;
	}

	/**
	 *
	 * Temporary method - will be removed
	 * @return BookingData - sailing history
	 */
	public BookingData createSailingHistory() {
		BookingData bookingData = new BookingData();

		bookingData.bookedBySailor("The Sailor");
		bookingData.brand("Virgin");
		bookingData.sailDate(LocalDate.now());
		bookingData.dateBooked(LocalDate.now());
		bookingData.reservationNumber("123");
		bookingData.currency("USD");
		bookingData.numberofAcessibleStaterooms(0);
		bookingData.numberofGuests(1);
		bookingData.daysonSailing(5);
		bookingData.disembarkationDate(LocalDate.now());
		bookingData.onboardSpend("100.00");
		bookingData.embarkationDate(LocalDate.now());
		bookingData.prepaidGratuities("None");
		bookingData.vacationProtection("Yes");
		bookingData.preCruiseTransfer("Air");
		bookingData.postCruiseTransfer("Hotel");
		bookingData.flightPurchase("Yes");
		bookingData.numberofStateroom(1);
		bookingData.ship("Virgin");
		bookingData.status("BOOK");
		bookingData.sailingPackage("All Inclusive");
		return bookingData;
	}
}
