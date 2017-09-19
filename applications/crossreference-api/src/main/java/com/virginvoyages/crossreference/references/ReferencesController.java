package com.virginvoyages.crossreference.references;

import java.util.List;

import javax.validation.constraints.NotNull;

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
import com.virginvoyages.assembly.ReferencesAssembly;
import com.virginvoyages.crossreference.types.ReferenceType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Api(value = "References", tags = "References", description = "References operations")
@Slf4j
@RequiredArgsConstructor
@ExposesResourceFor(References.class)
public class ReferencesController {

	@Autowired
	private MockCrossReferenceAPI mockAPI;
	
	@Autowired
	private ReferencesAssembly referencesAssembly;
	
	@ApiOperation(value = "", notes = "Add a new `Reference`.", response = Void.class, tags={ "Reference", })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Created", response = Void.class),
        @ApiResponse(code = 405, message = "Invalid input", response = Void.class) })
    @RequestMapping(value = "/references",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
	public ResponseEntity<Void> addReference(
			@ApiParam(value = "Reference object that needs to be created", required = true) @RequestBody Reference body,
			@ApiParam(value = "Correlation ID across the enterprise application components.") @RequestHeader(value = "X-Correlation-ID", required = false) String xCorrelationID,
			@ApiParam(value = "Application identifier of client.") @RequestHeader(value = "X-VV-Client-ID", required = false) String xVVClientID) {
		
		log.debug("Adding Reference");
		referencesAssembly.addReference(body);
	//	mockAPI.addReference(body);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@ApiOperation(value = "", notes = "Remove the Reference", response = Void.class, tags={ "Reference", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful response", response = Void.class),
        @ApiResponse(code = 404, message = "Reference not found", response = Void.class) })
    @RequestMapping(value = "/references/{referenceID}",
        method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteReferenceByID(
			@ApiParam(value = "The reference identifier", required = true) @PathVariable("referenceID") String referenceID,
			@ApiParam(value = "Correlation ID across the enterprise application components.") @RequestHeader(value = "X-Correlation-ID", required = false) String xCorrelationID,
			@ApiParam(value = "Application identifier of client.") @RequestHeader(value = "X-VV-Client-ID", required = false) String xVVClientID) {
		
		mockAPI.deleteReferenceByID(referenceID);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@ApiOperation(value = "Find reference by ID", notes = "Returns a reference for a specified reference identity.  This identity is a univeral reference identity.", response = ReferenceType.class, tags={ "Reference", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful response", response = ReferenceType.class) })
    @RequestMapping(value = "/references/{referenceID}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
	public ResponseEntity<Reference> findReferenceByID(
			@ApiParam(value = "The reference identifier", required = true) @PathVariable("referenceID") String referenceID,
			//@ApiParam(value = "ID of Reference that needs to be fetched", required = true) @PathVariable("referenceID2") Long referenceID2,
			@ApiParam(value = "Correlation ID across the enterprise application components.") @RequestHeader(value = "X-Correlation-ID", required = false) String xCorrelationID,
			@ApiParam(value = "Application identifier of client.") @RequestHeader(value = "X-VV-Client-ID", required = false) String xVVClientID) {
		
		return new ResponseEntity<Reference>(referencesAssembly.findReferenceID(referenceID),HttpStatus.OK);
	//	return new ResponseEntity<Reference>(mockAPI.findReferenceByID(referenceID),HttpStatus.OK);
	}

	@ApiOperation(value = "", notes = "Gets `Reference` objects.", response = References.class, tags={ "Reference", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful response", response = References.class) })
    @RequestMapping(value = "/references",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
	public ResponseEntity<References> findReferences(
			@ApiParam(value = "Correlation ID across the enterprise application components.") @RequestHeader(value = "X-Correlation-ID", required = false) String xCorrelationID,
			@ApiParam(value = "Application identifier of client.") @RequestHeader(value = "X-VV-Client-ID", required = false) String xVVClientID,
			@ApiParam(value = "") @RequestParam(value = "page", required = false) Integer page,
			@ApiParam(value = "") @RequestParam(value = "size", required = false) Integer size) {
		
		return new ResponseEntity<References>(mockAPI.findReferences(page,size),HttpStatus.OK);
	}

	@ApiOperation(value = "", notes = "Returns one or more references", response = Reference.class, responseContainer = "List", tags={ "Reference", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful response", response = Reference.class) })
    @RequestMapping(value = "/references/search/findByMaster",
        method = RequestMethod.GET)
	public ResponseEntity<List<Reference>> findReferencesMaster(
			@NotNull @ApiParam(value = "The master ID to search with.", required = true) @RequestParam(value = "masterID", required = true) String masterID,
			@ApiParam(value = "Correlation ID across the enterprise application components.") @RequestHeader(value = "X-Correlation-ID", required = false) String xCorrelationID,
			@ApiParam(value = "Application identifier of client.") @RequestHeader(value = "X-VV-Client-ID", required = false) String xVVClientID,
			@ApiParam(value = "The optional target source identifier.  Supplying this narrows the results to return only the matching target type.") @RequestParam(value = "targetSourceID", required = false) String targetSourceID) {
		
		return new ResponseEntity<List<Reference>>(mockAPI.findReferencesByMaster(masterID, targetSourceID),HttpStatus.OK);
	}

	@ApiOperation(value = "", notes = "Returns one or more references", response = Reference.class, responseContainer = "List", tags={ "Reference", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful response", response = Reference.class) })
    @RequestMapping(value = "/references/search/findBySource",
        method = RequestMethod.GET)
	public ResponseEntity<List<Reference>> findReferencesSource(
			@NotNull @ApiParam(value = "The identity to search for.", required = true) @RequestParam(value = "nativeSourceID", required = true) String nativeSourceID,
			@NotNull @ApiParam(value = "The source of the native identity being searched.", required = true) @RequestParam(value = "sourceID", required = true) String sourceID,
			@ApiParam(value = "Correlation ID across the enterprise application components.") @RequestHeader(value = "X-Correlation-ID", required = false) String xCorrelationID,
			@ApiParam(value = "Application identifier of client.") @RequestHeader(value = "X-VV-Client-ID", required = false) String xVVClientID,
			@ApiParam(value = "The type of the native identity being searched.") @RequestParam(value = "typeID", required = false) String typeID,
			@ApiParam(value = "The optional target source identifier.  Supplying this narrows the results to return only the matching target type.") @RequestParam(value = "targetSourceID", required = false) String targetSourceID) {
		
		//TODO isn't sourceid and targetsourceid the same here ?
		return new ResponseEntity<List<Reference>>(mockAPI.findReferencesBySource(nativeSourceID, sourceID, typeID, targetSourceID),HttpStatus.OK);
	}

	@ApiOperation(value = "", notes = "Merge references.  SOR specific logic of deleting the duplicate record is callers responsibility.", response = Reference.class, responseContainer = "List", tags={ "Reference", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful response", response = Reference.class) })
    @RequestMapping(value = "/references/merge/mergeByMaster",
        method = RequestMethod.POST)
	public ResponseEntity<List<Reference>> mergeReferencesMaster(
			@NotNull @ApiParam(value = "The master ID to merge from.", required = true) @RequestParam(value = "fromMasterID", required = true) String fromMasterID,
			@NotNull @ApiParam(value = "The master ID to merge towards.", required = true) @RequestParam(value = "toMasterID", required = true) String toMasterID,
			@ApiParam(value = "Correlation ID across the enterprise application components.") @RequestHeader(value = "X-Correlation-ID", required = false) String xCorrelationID,
			@ApiParam(value = "Application identifier of client.") @RequestHeader(value = "X-VV-Client-ID", required = false) String xVVClientID) {
		
		//TODO call mock
		return new ResponseEntity<List<Reference>>(HttpStatus.OK);
	}

	@ApiOperation(value = "", notes = "Merge references.  SOR specific logic of deleting the duplicate record is callers responsibility.", response = Reference.class, responseContainer = "List", tags={ "Reference", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful response", response = Reference.class) })
    @RequestMapping(value = "/references/merge/mergeBySource",
        method = RequestMethod.POST)
	public ResponseEntity<List<Reference>> mergeReferencesSource(
			@NotNull @ApiParam(value = "The source ID of the source.", required = true) @RequestParam(value = "sourceID", required = true) String sourceID,
			@NotNull @ApiParam(value = "The native source ID to merge from.", required = true) @RequestParam(value = "fromNativeID", required = true) String fromNativeID,
			@NotNull @ApiParam(value = "The native source ID to merge towards.", required = true) @RequestParam(value = "toNativeID", required = true) String toNativeID,
			@ApiParam(value = "Correlation ID across the enterprise application components.") @RequestHeader(value = "X-Correlation-ID", required = false) String xCorrelationID,
			@ApiParam(value = "Application identifier of client.") @RequestHeader(value = "X-VV-Client-ID", required = false) String xVVClientID) {
		
		//TODO call mock
		return new ResponseEntity<List<Reference>>(HttpStatus.OK);
	}

	 @ApiOperation(value = "", notes = "Update a `Reference` object.", response = Void.class, tags={ "Reference", })
	    @ApiResponses(value = { 
	        @ApiResponse(code = 400, message = "Invalid ID supplied", response = Void.class),
	        @ApiResponse(code = 404, message = "Reference not found", response = Void.class),
	        @ApiResponse(code = 405, message = "Validation exception", response = Void.class) })
	    @RequestMapping(value = "/references/{referenceID}",
	        produces = { "application/json" }, 
	        consumes = { "application/json" },
	        method = RequestMethod.PUT)
	public ResponseEntity<Void> updateReference(
			@ApiParam(value = "The reference identifier", required = true) @PathVariable("referenceID") String referenceID,
			@ApiParam(value = "Reference object that needs to be updated", required = true) @RequestBody Reference body,
			@ApiParam(value = "Correlation ID across the enterprise application components.") @RequestHeader(value = "X-Correlation-ID", required = false) String xCorrelationID,
			@ApiParam(value = "Application identifier of client.") @RequestHeader(value = "X-VV-Client-ID", required = false) String xVVClientID) {
		
		mockAPI.updateReference(referenceID, body);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}
