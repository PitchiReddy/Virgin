package com.virginvoyages.sailor;


import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.virginvoyages.api.MockSailorAPI;
import com.virginvoyages.assembly.SailorAssembly;
import com.virginvoyages.crm.data.AccountData;
import com.virginvoyages.sailor.exceptions.DataNotFoundException;
import com.virginvoyages.shared.exceptions.MandatoryFieldsMissingException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * Controller class to handle API requests for operations related to Sailor.
 * @author rpraveen
 *
 */
@RestController
@Api(value = "Sailor", tags = "Sailor", description = "Core Sailor operations")
@Slf4j
@RequiredArgsConstructor
@ExposesResourceFor(Sailor.class)
public class SailorController {

    private final @NonNull
    EntityLinks entityLinks;

    @Autowired
    private MockSailorAPI mock;
 
    @Autowired
    private SailorAssembly sailorAssembly;
     
  
    @ApiOperation(value = "", notes = "Add a new `Sailor` to the SORs.", response = Sailor.class, responseContainer = "List", tags = {"Sailor",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful response", response = Sailor.class),
            @ApiResponse(code = 405, message = "Invalid input", response = Sailor.class)})
    @RequestMapping(
            value = "/sailors",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<Void> addSailor(
            @ApiParam(value = "Correlation ID across the enterprise application components.") @RequestHeader(value = "X-Correlation-ID", required = false) String xCorrelationID,
            @ApiParam(value = "Application identifier of client.") @RequestHeader(value = "X-VV-Client-ID", required = false) String xVVClientID,
            @ApiParam(value = "Sailor object that needs to be added to the SORs") @RequestBody Sailor body) {
        mock.addSailor(body);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
    

   /**
    * Returns Sailor Object based on sailor ID
    * @param sailorID - ID of Sailor that needs to be fetched
    * @param xCorrelationID - Correlation ID across the enterprise application components.
    * @param xVVClientID - Application identifier of client.
    * @return - Sailor object in response
    */
    @ApiOperation(value = "Find sailor by ID", notes = "Returns a sailor for a specified sailor identity.  This identity is a univeral Sailor identity.", response = Sailor.class, tags = {"Sailor",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful response", response = Sailor.class)})
    @RequestMapping(value = "/sailors/{sailorID}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<Resource<Sailor>> getSailorById(
            @ApiParam(value = "ID of Sailor that needs to be fetched", required = true) @PathVariable("sailorID") String sailorID,
            @ApiParam(value = "Correlation ID across the enterprise application components.") @RequestHeader(value = "X-Correlation-ID", required = false) String xCorrelationID,
            @ApiParam(value = "Application identifier of client.") @RequestHeader(value = "X-VV-Client-ID", required = false) String xVVClientID) {
        
        log.debug("Request to return sailor with ID {}", sailorID);
        if(StringUtils.isEmpty(sailorID)) 
        	throw new MandatoryFieldsMissingException();
        
        Sailor sailor = sailorAssembly.getSailorById(sailorID);
        
        return new ResponseEntity<Resource<Sailor>>(SailorResourceAssembler.createSailorResource(sailor, entityLinks), HttpStatus.OK);
    }
    
    @ApiOperation(value = "", notes = "Remove the Sailor from the SORs", response = Void.class, tags = {"Sailor",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful response", response = Void.class)})
    @RequestMapping(value = "/sailors/{sailorID}",
            method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteSailorById(@ApiParam(value = "Correlation ID across the enterprise application components.") @RequestHeader(value = "X-Correlation-ID", required = false) String xCorrelationID,
                                       @ApiParam(value = "Application identifier of client.") @RequestHeader(value = "X-VV-Client-ID", required = false) String xVVClientID,
                                       @ApiParam(value = "ID of Sailor that needs to be removed", required = true) @PathVariable("sailorID") String sailorID) {
    	
    	log.debug("Request to return sailor with ID {}", sailorID);
        if(StringUtils.isEmpty(sailorID)) 
        	throw new MandatoryFieldsMissingException();
        sailorAssembly.deleteSailorById(sailorID);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }


    @ApiOperation(value = "", notes = "Remove the Sailor from the SORs", response = Void.class, tags = {"Sailor",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful response", response = Void.class)})
    @RequestMapping(value = "/sailors",
            method = RequestMethod.DELETE)
    ResponseEntity<Void> sailorsDelete(@ApiParam(value = "Correlation ID across the enterprise application components.") @RequestHeader(value = "X-Correlation-ID", required = false) String xCorrelationID,
                                       @ApiParam(value = "Application identifier of client.") @RequestHeader(value = "X-VV-Client-ID", required = false) String xVVClientID,
                                       @ApiParam(value = "Sailor object that needs to be removed from the SORs") @RequestBody Sailor body) {
    	
    	log.debug("Request to return sailor with ID {}", body.id());
        if(StringUtils.isEmpty(body.id())) 
        	throw new MandatoryFieldsMissingException();
        sailorAssembly.deleteSailorById(body.id());
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    
    /**
     * Searching  sailor details  based on given parameters if sailor Id is not there it display no data in Response Body 
     * @param firstName - First name to search Account with 
     * @param lastName - Last name to search Account with 
     * @param dateofBirth - Date of Birth to search Account with 
     * @param email - email to search Account with 
     * @param mobileNumber - Mobile Number to search Account with  
     * @returns List of sailors
    */
    @ApiOperation(value = "", notes = "Searches for one or more `Sailor` objects. Optional query param of **size** determines size of returned array", response = Sailor.class, responseContainer = "List", tags = {"Sailor",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful response", response = Sailor.class)})
    @RequestMapping(value = "/sailors/find",
            method = RequestMethod.GET)
     ResponseEntity<List<Sailor>> sailorsFindGet(@ApiParam(value = "Correlation ID across the enterprise application components.") @RequestHeader(value = "X-Correlation-ID", required = false) String xCorrelationID,
                                                @ApiParam(value = "Application identifier of client.") @RequestHeader(value = "X-VV-Client-ID", required = false) String xVVClientID,
                                                @ApiParam(value = "Loyalty Identifier") @RequestParam(value = "loyaltyID", required = false) String loyaltyID,
                                                @ApiParam(value = "Email of Sailor") @RequestParam(value = "email", required = false) String email,
                                                @ApiParam(value = "DOB of Sailor") @RequestParam(value = "dateofBirth", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateofBirth,
                                                @ApiParam(value = "First Name of Sailor") @RequestParam(value = "firstName", required = false) String firstName,
                                                @ApiParam(value = "Last Name of Sailor") @RequestParam(value = "lastName", required = false) String lastName,
                                                @ApiParam(value = "Mobile number of Sailor") @RequestParam(value = "mobileNumber", required = false) String mobileNumber) {
    
    	if (StringUtils.isEmpty(email) && dateofBirth == null && StringUtils.isEmpty(firstName)
				&& StringUtils.isEmpty(lastName) && StringUtils.isEmpty(mobileNumber)) {
			throw new MandatoryFieldsMissingException();
		}
		
    	AccountData accountData = setRequestParamsInAccountData(firstName, lastName, dateofBirth, email, mobileNumber);
    	
    	List<Sailor> listOfSailors = sailorAssembly.findSailors(accountData);
		
		if(listOfSailors.size() == 0) {
			throw new DataNotFoundException();
		}
		return new ResponseEntity<List<Sailor>>(listOfSailors, HttpStatus.OK);
    }


    /**
    * Fetching sailor details  based on given parameters if sailor Id is not there create new sailor Record
    * @param firstName - First name to search Account with 
    * @param lastName - Last name to search Account with 
    * @param dateofBirth - Date of Birth to search Account with 
    * @param email - email to search Account with 
    * @param mobileNumber - Mobile Number to search Account with  
    * @returns  Sailor
    */
    @ApiOperation(value = "", notes = "Searches for one or more `Sailor` objects. If the sailor is not found, the Sailor is created in the SORs and returned.  If there are multiple matches this operation returns the first Sailor.", response = Sailor.class, tags = {"Sailor",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful response", response = Sailor.class)})
    @RequestMapping(value = "/sailors/findOrCreate",
            method = RequestMethod.GET)
    ResponseEntity<Resource<Sailor>> sailorsFindOrCreateGet(@ApiParam(value = "Correlation ID across the enterprise application components.") @RequestHeader(value = "X-Correlation-ID", required = false) String xCorrelationID,
                                                  @ApiParam(value = "Application identifier of client.") @RequestHeader(value = "X-VV-Client-ID", required = false) String xVVClientID,
                                                  @ApiParam(value = "Loyalty Identifier") @RequestParam(value = "loyaltyID", required = false) String loyaltyID,
                                                  @ApiParam(value = "Email of Sailor") @RequestParam(value = "email", required = true) String email,
                                                  @ApiParam(value = "DOB of Sailor") @RequestParam(value = "dateofBirth", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateofBirth,
                                                  @ApiParam(value = "First Name of Sailor") @RequestParam(value = "firstName", required = true) String firstName,
                                                  @ApiParam(value = "Last Name of Sailor") @RequestParam(value = "lastName", required = true) String lastName,
                                                  @ApiParam(value = "Mobile number of Sailor") @RequestParam(value = "mobileNumber", required = false) String mobileNumber) {

		
		if (StringUtils.isEmpty(email) || dateofBirth == null || StringUtils.isEmpty(firstName)
				|| StringUtils.isEmpty(lastName)) {
			throw new MandatoryFieldsMissingException();
		}
		
		AccountData accountData = setRequestParamsInAccountData(firstName, lastName, dateofBirth, email, mobileNumber);
		List<Sailor> listOfSailors = sailorAssembly.findSailors(accountData);
		
		Sailor sailor = listOfSailors.isEmpty() ? sailorAssembly.createSailor(accountData):listOfSailors.get(0);
		
		return new ResponseEntity<Resource<Sailor>>(SailorResourceAssembler.createSailorResource(sailor, entityLinks), HttpStatus.OK);
		
	}

    @ApiOperation(value = "", notes = "Gets `Sailor` objects. Optional query param of **size** determines size of returned array", response = Sailors.class, tags = {"Sailor",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful response", response = Sailors.class)})
    @RequestMapping(value = "/sailors",
            method = RequestMethod.GET)
    ResponseEntity<Sailors> sailorsGet(@ApiParam(value = "Correlation ID across the enterprise application components.") @RequestHeader(value = "X-Correlation-ID", required = false) String xCorrelationID,
                                       @ApiParam(value = "Application identifier of client.") @RequestHeader(value = "X-VV-Client-ID", required = false) String xVVClientID,
                                       @ApiParam(value = "") @RequestParam(value = "page", required = false) Integer page,
                                       @ApiParam(value = "") @RequestParam(value = "size", required = false) Integer size) {
        return new ResponseEntity<Sailors>(mock.sailorsGet(page, size), HttpStatus.OK);
    }

    @ApiOperation(value = "Update an existing Sailor", notes = "", response = Void.class, tags = {"Sailor",})
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid ID supplied", response = Void.class),
            @ApiResponse(code = 404, message = "Sailor not found", response = Void.class),
            @ApiResponse(code = 405, message = "Validation exception", response = Void.class)})
    @RequestMapping(value = "/sailors",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.PUT)
    ResponseEntity<Void> updateSailor(@ApiParam(value = "Correlation ID across the enterprise application components.") @RequestHeader(value = "X-Correlation-ID", required = false) String xCorrelationID,
                                      @ApiParam(value = "Application identifier of client.") @RequestHeader(value = "X-VV-Client-ID", required = false) String xVVClientID,
                                      @ApiParam(value = "Sailor object that needs to be updated in the SOR") @RequestBody Sailor body) {
        mock.updateSailor(body);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
    
         
    /**
     * Creates account data object with given values.
     * @param firstName
     * @param lastName
     * @param dob
     * @param email
     * @param mobileNumber
     * @return
     */
    private AccountData setRequestParamsInAccountData(String firstName,String lastName, LocalDate dob, String email,String mobileNumber) {
    	
    	// TODO optimize with fluent, dynamic
    	AccountData accountData = new AccountData();
    	accountData.firstName(firstName);
    	accountData.lastName(lastName);
    	accountData.dateofBirth(dob);
    	accountData.primaryEmail(email);
    	accountData.mobileNumber(mobileNumber);
    	return accountData;
    	
    }
        
}
