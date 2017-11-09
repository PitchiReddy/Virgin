package com.virginvoyages.booking;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.netflix.feign.FeignAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import com.virginvoyages.assembly.BookingAssembly;
import com.virginvoyages.exceptions.DataNotFoundException;
import com.virginvoyages.sailor.helper.MockDataHelper;

@RunWith(SpringRunner.class)
@WebMvcTest(BookingController.class)
@ImportAutoConfiguration({ FeignAutoConfiguration.class })
public class BookingControllerTest {
	
	@Autowired
	private MockMvc mvc;

	@MockBean
	BookingAssembly bookingAssembly;
	
	@Autowired
	private MockDataHelper mockDataHelper;


    @Test  
    public void givenSailorWithSailorIDHasSailorBookingsFindSailorBookingsShouldReturnSailorBookings() throws Exception {
        // Mock Setup
    	String sailorID = mockDataHelper.getSailorId();

		given(bookingAssembly.getSailingHistory(sailorID))
				.willReturn(mockDataHelper.createSailingHistory(true));

		// Test
		mvc.perform(get("/sailors/" + sailorID + "/sailingHistory").contentType("application/json"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._embedded.bookings", hasSize(greaterThan(0))))
				.andExpect(jsonPath("$._embedded.bookings[*].status", hasItems("BOOK")));
		
	}
    
    @Test  
    public void givenSailorWithSailorIDHasSailorBookingsFindSailorBookingsShouldReturnEmptyList() throws Exception {
        // Mock Setup
    	String sailorID = mockDataHelper.getSailorId();

		given(bookingAssembly.getSailingHistory(sailorID))
				.willReturn(mockDataHelper.createSailingHistory(false));

		// Test
		mvc.perform(get("/sailors/" + sailorID + "/sailingHistory").contentType("application/json"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$._embedded.bookings", hasSize(0)));
		
	}
    
    @Test 
    public void givenInvalidSailorIdFindSailorBookingsShouldThrowSomeException() throws Exception {
    	// Mock Setup
    	String inValidSailorID = mockDataHelper.getSailorId();
  
    	given(bookingAssembly.getSailingHistory(inValidSailorID))
		.willThrow(new DataNotFoundException());
	
    	// Test
    	mvc.perform(get("/sailors/"+inValidSailorID +"/sailingHistory").contentType("application/json"))
			.andExpect(status().is(HttpStatus.NOT_FOUND.value()));
	}
  
}
