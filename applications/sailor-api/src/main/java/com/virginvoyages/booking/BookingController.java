package com.virginvoyages.booking;

import com.virginvoyages.assembly.BookingAssembly;
import com.virginvoyages.booking.model.Bookings;
import com.virginvoyages.model.Page;
import com.virginvoyages.sailor.api.MockSailorAPI;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class to handle API requests for sailor booking methods.
 * @author pbovilla
 *
 */
@RestController
@Api(value = "Booking", description = "Bookings for a Sailor", tags = "Booking")
public class BookingController {

    @Autowired
    private MockSailorAPI mock;
    
    @Autowired
    private BookingAssembly bookingAssembly;

    @ApiOperation(value = "", notes = "", response = Bookings.class, tags = {"Booking",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Gets all the current bookings associated with the Sailor", response = Bookings.class)})
    @RequestMapping(value = "/sailors/{sailorID}/bookings",
            method = RequestMethod.GET)
    public ResponseEntity<Bookings> findBookingsBySailor(@ApiParam(value = "The sailor identifier number", required = true) @PathVariable("sailorID") String sailorID,
                                                         @ApiParam(value = "Correlation ID across the enterprise application components.") @RequestHeader(value = "X-Correlation-ID", required = false) String xCorrelationID,
                                                         @ApiParam(value = "Application identifier of client.") @RequestHeader(value = "X-VV-Client-ID", required = false) String xVVClientID,
                                                         @ApiParam(value = "") @RequestParam(value = "page", required = false) Integer page,
                                                         @ApiParam(value = "") @RequestParam(value = "size", required = false) Integer size) {
        return new ResponseEntity<Bookings>(mock.sailorsSailorIDBookingsGet(sailorID, page, size), HttpStatus.OK);
    }

    /**
	 * Returns Bookings data based on sailor ID
	 * @param sailorID - ID of Sailor that needs to be fetched
	 * @param xCorrelationID - Correlation ID across the enterprise application components.
	 * @param xVVClientID - Application identifier of client.
	 * @return - Bookings object in response
	 */
    @ApiOperation(value = "", notes = "", response = Bookings.class, tags = {"SailingHistory",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Gets all the sailing history associated with the Sailor", response = Bookings.class)})
    @RequestMapping(value = "/sailors/{sailorID}/sailingHistory",
            method = RequestMethod.GET)
    public ResponseEntity<Bookings> findSailingHistoryBySailor(@ApiParam(value = "The sailor identifier number", required = true) @PathVariable("sailorID") String sailorID,
                                                               @ApiParam(value = "Correlation ID across the enterprise application components.") @RequestHeader(value = "X-Correlation-ID", required = false) String xCorrelationID,
                                                               @ApiParam(value = "Application identifier of client.") @RequestHeader(value = "X-VV-Client-ID", required = false) String xVVClientID,
                                                               @ApiParam(value = "") @RequestParam(value = "page", required = false) Integer page,
                                                               @ApiParam(value = "") @RequestParam(value = "size", required = false) Integer size) {
    	Bookings bookings = new Bookings().page(new Page().size(size))
                .embedded(bookingAssembly.getSailingHistory(sailorID));
    	return new ResponseEntity<Bookings>(bookings, HttpStatus.OK);
    }


}
