package com.virginvoyages.contact;


import com.virginvoyages.assembly.ContactMethodsAssembly;
import com.virginvoyages.contact.model.ContactMethods;
import com.virginvoyages.model.Page;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class to handle API requests for sailor contact methods.
 * @author pbovilla
 *
 */
@RestController
@Api(value = "Contact Method", description = "Contact Methods for a Sailor", tags = "Contact Method")
public class ContactMethodController {

	@Autowired
	private ContactMethodsAssembly contactMethodsAssembly;

	/**
	 * Returns Sailor Contact Methods data based on sailor ID
	 * @param sailorID - ID of Sailor that needs to be fetched
	 * @param xCorrelationID - Correlation ID across the enterprise application components.
	 * @param xVVClientID - Application identifier of client.
	 * @return - ContactMethods object in response
	 */
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

    	ContactMethods contactMethods = new ContactMethods().page(new Page().size(size))
                .embedded(contactMethodsAssembly.findSailorsContactMethods(sailorID));
        return new ResponseEntity<ContactMethods>(contactMethods, HttpStatus.OK);
    }


}
