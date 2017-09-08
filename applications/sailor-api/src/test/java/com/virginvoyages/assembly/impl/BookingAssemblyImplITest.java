package com.virginvoyages.assembly.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.virginvoyages.booking.BookingsEmbedded;
import com.virginvoyages.dao.impl.BookingsDAOImpl;
import com.virginvoyages.sailor.helper.MockDataHelper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookingAssemblyImplITest {

	@Mock
    private BookingsDAOImpl bookingsDAOImpl;
	
    @InjectMocks
    private BooikngAssemblyImpl booikngAssemblyImpl;
	
	@Autowired
	private MockDataHelper mockDataHelper;
	
	@Before
    public void setUp() throws Exception {
         MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void givenValidSailorIdWithSailorBookingsFindSailorBookingsShouldReturnSailingHistory() {
		when(bookingsDAOImpl.getSailingHistory(any(String.class))).thenReturn(mockDataHelper.getSailorBookingQueryResultsData(true));
		BookingsEmbedded bookingsEmbedded = booikngAssemblyImpl.getSailingHistory(mockDataHelper.getSailorId());
		assertThat(bookingsEmbedded.bookings(), is(notNullValue()));
        assertThat(bookingsEmbedded.bookings().size(), equalTo(1));
	}
	
	@Test
	public void givenValidSailorIdWithSailorBookingsFindSailorBookingsShouldReturnEmptyList() {
		when(bookingsDAOImpl.getSailingHistory(any(String.class))).thenReturn(mockDataHelper.getSailorBookingQueryResultsData(false));
		BookingsEmbedded bookingsEmbedded = booikngAssemblyImpl.getSailingHistory(mockDataHelper.getSailorId());
		assertThat(bookingsEmbedded.bookings(), is(notNullValue()));
        assertThat(bookingsEmbedded.bookings().size(), equalTo(0));
	}
}
