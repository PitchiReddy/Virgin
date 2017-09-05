package com.virginvoyages.loyalty;

import com.virginvoyages.api.MockSailorAPI;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "Loyalty", description = "Loyalty for a Sailor", tags = "Loyalty")
public class LoyaltyController {

    @Autowired
    private MockSailorAPI mock;

    @ApiOperation(value = "", notes = "", response = Loyalties.class, tags = {"Loyalty",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Gets the current Loyalty objects associated with this Sailor", response = Loyalties.class)})
    @RequestMapping(value = "/sailors/{sailorID}/loyalty",
            method = RequestMethod.GET)
    public ResponseEntity<Loyalties> findLoyaltiesBySailor(@ApiParam(value = "The sailor identifier number", required = true) @PathVariable("sailorID") String sailorID,
                                                           @ApiParam(value = "Correlation ID across the enterprise application components.") @RequestHeader(value = "X-Correlation-ID", required = false) String xCorrelationID,
                                                           @ApiParam(value = "Application identifier of client.") @RequestHeader(value = "X-VV-Client-ID", required = false) String xVVClientID,
                                                           @ApiParam(value = "") @RequestParam(value = "page", required = false) Integer page,
                                                           @ApiParam(value = "") @RequestParam(value = "size", required = false) Integer size) {

        return new ResponseEntity<Loyalties>(mock.sailorsSailorIDLoyaltyGet(sailorID, page, size), HttpStatus.OK);
    }

}
