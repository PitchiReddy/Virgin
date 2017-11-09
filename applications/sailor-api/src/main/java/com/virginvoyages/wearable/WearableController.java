package com.virginvoyages.wearable;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.virginvoyages.sailor.api.MockSailorAPI;

@RestController
@Api(value = "Wearable", description = "Wearable for a Sailor", tags = "Wearables")
public class WearableController {

    @Autowired
    private MockSailorAPI mock;

    @ApiOperation(value = "", notes = "", response = Wearables.class, tags = {"Wearables",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Gets the wearables associated with this Sailor", response = Wearables.class)})
    @RequestMapping(value = "/sailors/{sailorID}/wearables",
            method = RequestMethod.GET)
    public ResponseEntity<Wearables> findWearablesBySailor(@ApiParam(value = "The sailor identifier number", required = true) @PathVariable("sailorID") String sailorID,
                                                           @ApiParam(value = "Correlation ID across the enterprise application components.") @RequestHeader(value = "X-Correlation-ID", required = false) String xCorrelationID,
                                                           @ApiParam(value = "Application identifier of client.") @RequestHeader(value = "X-VV-Client-ID", required = false) String xVVClientID,
                                                           @ApiParam(value = "") @RequestParam(value = "page", required = false) Integer page,
                                                           @ApiParam(value = "") @RequestParam(value = "size", required = false) Integer size) {
        return new ResponseEntity<Wearables>(mock.sailorsSailorIDWearablesGet(sailorID, page, size), HttpStatus.OK);
    }

}
