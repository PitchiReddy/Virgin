package com.virginvoyages.visa;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.virginvoyages.sailor.api.MockSailorAPI;
import com.virginvoyages.visa.model.Visas;

@RestController
@Api(value = "Visa", description = "Visa for a Sailor", tags = "Visa")
public class VisaController {

    @Autowired
    private MockSailorAPI mock;

    @ApiOperation(value = "", notes = "", response = Visas.class, tags = {"Visa",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Gets the current Visa objects associated with this Sailor", response = Visas.class)})
    @RequestMapping(value = "/sailors/{sailorID}/visas",
            method = RequestMethod.GET)
    public ResponseEntity<Visas> findVisasBySailor(@ApiParam(value = "The sailor identifier number", required = true) @PathVariable("sailorID") String sailorID,
                                                   @ApiParam(value = "Correlation ID across the enterprise application components.") @RequestHeader(value = "X-Correlation-ID", required = false) String xCorrelationID,
                                                   @ApiParam(value = "Application identifier of client.") @RequestHeader(value = "X-VV-Client-ID", required = false) String xVVClientID,
                                                   @ApiParam(value = "") @RequestParam(value = "page", required = false) Integer page,
                                                   @ApiParam(value = "") @RequestParam(value = "size", required = false) Integer size) {
        return new ResponseEntity<Visas>(mock.sailorsSailorIDVisasGet(sailorID, page, size), HttpStatus.OK);
    }

}
