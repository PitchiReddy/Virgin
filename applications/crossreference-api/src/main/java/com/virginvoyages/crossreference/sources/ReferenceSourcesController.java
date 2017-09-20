package com.virginvoyages.crossreference.sources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.virginvoyages.api.MockCrossReferenceAPI;
import com.virginvoyages.assembly.ReferenceSourcesAssembly;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller class to handle API requests for reference source methods.
 * @author pbovilla
 *
 */
@RestController
@Api(value = "ReferenceSource", tags = "ReferenceSource", description = "Reference Source operations")
@Slf4j
@RequiredArgsConstructor
@ExposesResourceFor(ReferenceSource.class)
public class ReferenceSourcesController {
	
	@Autowired
	private MockCrossReferenceAPI mockAPI;
	
	@Autowired
	private ReferenceSourcesAssembly referenceSourcesAssembly;
	
	/**
	 * @param ReferenceSource
	 * @param xCorrelationID - Correlation ID across the enterprise application components.
	 * @param xVVClientID - Application identifier of client.
	 * @return
	 */
	@ApiOperation(value = "", notes = "Add a new `ReferenceSource`.", response = Void.class, tags={ "ReferenceSource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Created", response = Void.class),
        @ApiResponse(code = 405, message = "Invalid input", response = Void.class) })
    @RequestMapping(value = "/sources",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
	public ResponseEntity<Void> addReferenceSource(
			@ApiParam(value = "Reference object that needs to be created", required = true) @RequestBody ReferenceSource body,
			@ApiParam(value = "Correlation ID across the enterprise application components.") @RequestHeader(value = "X-Correlation-ID", required = false) String xCorrelationID,
			@ApiParam(value = "Application identifier of client.") @RequestHeader(value = "X-VV-Client-ID", required = false) String xVVClientID) {
        
		log.debug("Adding Reference Source");
		referenceSourcesAssembly.addReferenceSource(body);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@ApiOperation(value = "", notes = "Remove the ReferenceSource", response = Void.class, tags={ "ReferenceSource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful response", response = Void.class),
        @ApiResponse(code = 404, message = "Reference not found", response = Void.class) })
    @RequestMapping(value = "/sources/{referenceSourceID}",
        method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteReferenceSourceByID(
			@ApiParam(value = "The reference source identifier", required = true) @PathVariable("referenceSourceID") String referenceSourceID,
			@ApiParam(value = "Correlation ID across the enterprise application components.") @RequestHeader(value = "X-Correlation-ID", required = false) String xCorrelationID,
			@ApiParam(value = "Application identifier of client.") @RequestHeader(value = "X-VV-Client-ID", required = false) String xVVClientID) {

		mockAPI.deleteReferenceSourceByID(referenceSourceID);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@ApiOperation(value = "", notes = "Gets `Source` objects.", response = ReferenceSource.class, responseContainer = "List", tags={ "ReferenceSource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful response", response = ReferenceSource.class) })
    @RequestMapping(value = "/sources",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
	public ResponseEntity<List<ReferenceSource>> findSources(
			@ApiParam(value = "Correlation ID across the enterprise application components.") @RequestHeader(value = "X-Correlation-ID", required = false) String xCorrelationID,
			@ApiParam(value = "Application identifier of client.") @RequestHeader(value = "X-VV-Client-ID", required = false) String xVVClientID,
			@ApiParam(value = "") @RequestParam(value = "page", required = false) Integer page,
			@ApiParam(value = "") @RequestParam(value = "size", required = false) Integer size) {
        
		
		return new ResponseEntity<List<ReferenceSource>>(mockAPI.findSources(),HttpStatus.OK);
	}

	@ApiOperation(value = "Find reference source by ID", notes = "Returns a reference source for a specified reference source identity.  This identity is a univeral reference identity.", response = ReferenceSource.class, tags={ "ReferenceSource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful response", response = ReferenceSource.class) })
    @RequestMapping(value = "/sources/{referenceSourceID}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
	public ResponseEntity<ReferenceSource> getReferenceSourceByID(
			@ApiParam(value = "The reference source identifier", required = true) @PathVariable("referenceSourceID") String referenceSourceID,
			@ApiParam(value = "Correlation ID across the enterprise application components.") @RequestHeader(value = "X-Correlation-ID", required = false) String xCorrelationID,
			@ApiParam(value = "Application identifier of client.") @RequestHeader(value = "X-VV-Client-ID", required = false) String xVVClientID) {

		return new ResponseEntity<ReferenceSource>(mockAPI.findReferenceSourceByID(referenceSourceID),HttpStatus.OK);
	}

	@ApiOperation(value = "", notes = "Update a `ReferenceSource` object.", response = Void.class, tags={ "ReferenceSource", })
    @ApiResponses(value = { 
        @ApiResponse(code = 400, message = "Invalid ID supplied", response = Void.class),
        @ApiResponse(code = 404, message = "ReferenceSource not found", response = Void.class),
        @ApiResponse(code = 405, message = "Validation exception", response = Void.class) })
    @RequestMapping(value = "/sources/{referenceSourceID}",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.PUT)
	public ResponseEntity<Void> updateReferenceSource(
			@ApiParam(value = "The reference source identifier", required = true) @PathVariable("referenceSourceID") String referenceSourceID,
			@ApiParam(value = "Reference object that needs to be updated", required = true) @RequestBody ReferenceSource body,
			@ApiParam(value = "Correlation ID across the enterprise application components.") @RequestHeader(value = "X-Correlation-ID", required = false) String xCorrelationID,
			@ApiParam(value = "Application identifier of client.") @RequestHeader(value = "X-VV-Client-ID", required = false) String xVVClientID) {
        
		mockAPI.updateReferenceSource(referenceSourceID,body);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

}
