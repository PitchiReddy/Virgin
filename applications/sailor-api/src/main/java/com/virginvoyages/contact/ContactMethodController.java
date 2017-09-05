package com.virginvoyages.contact;

import com.virginvoyages.api.MockSailorAPI;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "Contact Method", description = "Contact Methods for a Sailor", tags = "Contact Method")
public class ContactMethodController {

    @Autowired
    private MockSailorAPI mock;

    @ApiOperation(value = "", notes = "", response = ContactMethods.class, tags = {"Contact Method",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Get all the contact methods for this Sailor", response = ContactMethods.class)})
    @RequestMapping(value = "/sailors/{sailorID}/contactMethod",
            method = RequestMethod.GET)
    public ResponseEntity<ContactMethods> findContactMethodsBySailor(@ApiParam(value = "The sailor identifier number", required = true) @PathVariable("sailorID") String sailorID,
                                                                     @ApiParam(value = "Correlation ID across the enterprise application components.") @RequestHeader(value = "X-Correlation-ID", required = false) String xCorrelationID,
                                                                     @ApiParam(value = "Application identifier of client.") @RequestHeader(value = "X-VV-Client-ID", required = false) String xVVClientID,
                                                                     @ApiParam(value = "") @RequestParam(value = "page", required = false) Integer page,
                                                                     @ApiParam(value = "") @RequestParam(value = "size", required = false) Integer size) {
        return new ResponseEntity<ContactMethods>(mock.sailorsSailorIDContactMethodGet(sailorID, page, size), HttpStatus.OK);
    }


}
