package com.virginvoyages.assembly.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.virginvoyages.assembly.BookingAssembly;
import com.virginvoyages.booking.BookingsEmbedded;
import com.virginvoyages.crm.data.BookingData;
import com.virginvoyages.crm.data.QueryResultsData;
import com.virginvoyages.dao.BookingsDAO;
import com.virginvoyages.sailor.helper.TestDataHelper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookingAssemblyImplIT {

	@Autowired
	private BookingAssembly bookingAssembly;
	
	@Autowired
	private BookingsDAO bookingsDAO;

	@Autowired
	private TestDataHelper testDataHelper;
	
	@Test
	public void givenValidSailorIdWithSailorBookingsFindSailorBookingsShouldReturnSailingHistory() {
		String sailorID = testDataHelper.getSailorIDWithSailingHistory();
		BookingsEmbedded bookingsEmbedded = bookingAssembly.getSailingHistory(sailorID);
		assertThat(bookingsEmbedded.bookings(), is(notNullValue()));
		assertThat(bookingsEmbedded.bookings().size(), is(not(0)));
	}
	
	@Test
	public void givenValidSailorIdWithSailorBookingsFindSailorBookingsShouldReturnPastBookingDate() {
		String sailorID = testDataHelper.getSailorIDWithSailingHistory();
		QueryResultsData<BookingData>  queryResultsData= bookingsDAO.getSailingHistory(sailorID);
		List<BookingData> bookingDataList= queryResultsData.records();
		for(BookingData bookingData: bookingDataList){
			assertThat(bookingData.dateBooked(), is(notNullValue()));
		}
	
	}
	
	@Test
	public void givenValidSailorIdWithoutSailorBookingsFindSailorBookingsShouldReturnEmptyList() {

	}
	
	@Test
	public void givenInvalidSailorIdFindSailorBookingsShouldThrowSomeException() {
		
	}
	
	@Test
	public void givenNoSailorIdFindSailorBookingsShouldThrowSomeException() {
		
	}
}
