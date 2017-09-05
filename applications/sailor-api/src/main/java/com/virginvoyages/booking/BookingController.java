package com.virginvoyages.booking;

import com.virginvoyages.api.MockSailorAPI;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "Booking", description = "Bookings for a Sailor", tags = "Booking")
public class BookingController {

    @Autowired
    private MockSailorAPI mock;

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
        return new ResponseEntity<Bookings>(mock.sailorsSailorIDSailingHistoryGet(sailorID, page, size), HttpStatus.OK);
    }


}
