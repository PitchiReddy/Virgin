package com.virginvoyages.household;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.virginvoyages.household.model.Households;
import com.virginvoyages.sailor.api.MockSailorAPI;

@RestController
@Api(value = "Household", description = "Households for a Sailor", tags = "Household")
public class HouseholdController {

    @Autowired
    private MockSailorAPI mock;

    @ApiOperation(value = "", notes = "", response = Households.class, tags = {"Household",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Gets all the Household members of this Sailor", response = Households.class)})
    @RequestMapping(value = "/sailors/{sailorID}/households",
            method = RequestMethod.GET)
    public ResponseEntity<Households> findHouseholdsBySailor(@ApiParam(value = "The sailor identifier number", required = true) @PathVariable("sailorID") String sailorID,
                                                             @ApiParam(value = "Correlation ID across the enterprise application components.") @RequestHeader(value = "X-Correlation-ID", required = false) String xCorrelationID,
                                                             @ApiParam(value = "Application identifier of client.") @RequestHeader(value = "X-VV-Client-ID", required = false) String xVVClientID,
                                                             @ApiParam(value = "") @RequestParam(value = "page", required = false) Integer page,
                                                             @ApiParam(value = "") @RequestParam(value = "size", required = false) Integer size) {
        return new ResponseEntity<Households>(mock.sailorsSailorIDHouseholdsGet(sailorID, page, size), HttpStatus.OK);
    }
}
