package com.virginvoyages.identification;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.virginvoyages.identification.model.Identifications;
import com.virginvoyages.sailor.api.MockSailorAPI;

@RestController
@Api(value = "Identification", description = "Identifications for a Sailor", tags = "Identification")
public class IdentificationController {

    @Autowired
    private MockSailorAPI mock;

    @ApiOperation(value = "", notes = "", response = Identifications.class, tags = {"Identification",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Gets the current Identification objects associated with this Sailor", response = Identifications.class)})
    @RequestMapping(value = "/sailors/{sailorID}/identification",
            method = RequestMethod.GET)
    public ResponseEntity<Identifications> findIdentificationBySailor(@ApiParam(value = "The sailor identifier number", required = true) @PathVariable("sailorID") String sailorID,
                                                                      @ApiParam(value = "Correlation ID across the enterprise application components.") @RequestHeader(value = "X-Correlation-ID", required = false) String xCorrelationID,
                                                                      @ApiParam(value = "Application identifier of client.") @RequestHeader(value = "X-VV-Client-ID", required = false) String xVVClientID,
                                                                      @ApiParam(value = "") @RequestParam(value = "page", required = false) Integer page,
                                                                      @ApiParam(value = "") @RequestParam(value = "size", required = false) Integer size) {
        return new ResponseEntity<Identifications>(mock.sailorsSailorIDIdentificationGet(sailorID, page, size), HttpStatus.OK);
    }


}
