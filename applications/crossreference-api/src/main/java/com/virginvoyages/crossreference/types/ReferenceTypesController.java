package com.virginvoyages.crossreference.types;

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

import com.virginvoyages.crossreference.assembly.ReferenceTypesAssembly;
import com.virginvoyages.exceptions.DataInsertionException;
import com.virginvoyages.exceptions.DataNotFoundException;
import com.virginvoyages.exceptions.MandatoryFieldsMissingException;
import com.virginvoyages.model.crossreference.ReferenceSource;
import com.virginvoyages.model.crossreference.ReferenceType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

/**
 * Controller class to handle API requests for operations related to
 * ReferenceTypes.
 * 
 * @author snarthu
 *
 */
@RestController
@Api(value = "ReferenceType", tags = "ReferenceType", description = "Reference Type operations")
@Slf4j
@RequiredArgsConstructor
@ExposesResourceFor(ReferenceType.class)
public class ReferenceTypesController {
	
	@Autowired
	private ReferenceTypesAssembly referenceTypesAssembly;

	/**
	 * It is adding new ReferenceType Record
	 * @param ReferenceType
	 * @param xCorrelationID
	 *            - Correlation ID across the enterprise application components.
	 * @param xVVClientID
	 *            - Application identifier of client.
	 * @return ReferenceType - returns a referenceType
	 */
	@ApiOperation(value = "", notes = "Add a new `ReferenceType`.", response = Void.class, tags = { "ReferenceType", })
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Created", response = Void.class),
			@ApiResponse(code = 405, message = "Invalid input", response = Void.class) })
	@RequestMapping(value = "/types", produces = { "application/json" },  method = RequestMethod.POST)
	public ResponseEntity<ReferenceType> addReferenceType(
			@ApiParam(value = "Reference Type object that needs to be created", required = true) @RequestBody ReferenceType body,
			@ApiParam(value = "Correlation ID across the enterprise application components.") @RequestHeader(value = "X-Correlation-ID", required = false) String xCorrelationID,
			@ApiParam(value = "Application identifier of client.") @RequestHeader(value = "X-VV-Client-ID", required = false) String xVVClientID) {
		
		log.debug("Adding Reference Type");
		if(StringUtils.isBlank(body.referenceType()) || StringUtils.isBlank(body.referenceSourceID())){
			throw new MandatoryFieldsMissingException();
		}
		ReferenceType referenceType =referenceTypesAssembly.addReferenceType(body);
		if(null == referenceType) {
			log.error("ReferenceType Not saved due to unknown reasons ==> "+body.referenceType());
			throw new DataInsertionException("ReferenceType Not Saved due to Unkown reasons");
		}
		return new ResponseEntity<ReferenceType>(referenceType,HttpStatus.OK);
	}

	/**
	 * @param referenceTypeID
	 *            - delete reference type by ID
	 * @param xCorrelationID
	 *            - Correlation ID across the enterprise application components.
	 * @param xVVClientID
	 *            - Application identifier of client.
	 * @return 
	 */
	@ApiOperation(value = "", notes = "Remove the ReferenceType", response = Void.class, tags = { "ReferenceType", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful response", response = Void.class),
			@ApiResponse(code = 404, message = "ReferenceType not found", response = Void.class) })
	@RequestMapping(value = "/types/{referenceTypeID}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteReferenceTypeByID(
			@ApiParam(value = "The reference type identifier", required = true) @PathVariable("referenceTypeID") String referenceTypeID,
			@ApiParam(value = "Correlation ID across the enterprise application components.") @RequestHeader(value = "X-Correlation-ID", required = false) String xCorrelationID,
			@ApiParam(value = "Application identifier of client.") @RequestHeader(value = "X-VV-Client-ID", required = false) String xVVClientID) {
		
		log.debug("deleting Reference Type");
		if(StringUtils.isBlank(referenceTypeID)) {
			throw new MandatoryFieldsMissingException();
		}
		referenceTypesAssembly.deleteReferenceTypeByID(referenceTypeID);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	/**
	 * @param referenceTypeID
	 *            - Find reference type by ID
	 * @param xCorrelationID
	 *            - Correlation ID across the enterprise application components.
	 * @param xVVClientID
	 *            - Application identifier of client.
	 * @return ReferenceType - returns a referenceType
	 */
	@ApiOperation(value = "Find reference type by ID", notes = "Returns a reference type for a specified reference type identity.  This identity is a univeral reference identity.", response = ReferenceType.class, tags = {
			"ReferenceType", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful response", response = ReferenceType.class) })
	@RequestMapping(value = "/types/{referenceTypeID}", produces = { "application/json" }, method = RequestMethod.GET)
	public ResponseEntity<ReferenceType> findReferenceTypeByID(
			@ApiParam(value = "The reference type identifier", required = true) @PathVariable("referenceTypeID") String referenceTypeID,
			@ApiParam(value = "Correlation ID across the enterprise application components.") @RequestHeader(value = "X-Correlation-ID", required = false) String xCorrelationID,
			@ApiParam(value = "Application identifier of client.") @RequestHeader(value = "X-VV-Client-ID", required = false) String xVVClientID) {
		
		log.debug("finding Reference Type By referenceTypeID");
		if(StringUtils.isBlank(referenceTypeID)) {
			throw new MandatoryFieldsMissingException();
		}
		ReferenceType referenceType = referenceTypesAssembly.findReferenceTypeByID(referenceTypeID);
		if(null == referenceType) {
			throw new DataNotFoundException();
		}
		return new ResponseEntity<ReferenceType>(referenceType,HttpStatus.OK);
	}

	/**
	 * @param referenceSourceName
	 * @param xCorrelationID - Correlation ID across the enterprise application components.
	 * @param xVVClientID - Application identifier of client.
	 * @return ReferenceSource returns a referenceSource
	 */
	@ApiOperation(value = "Find reference type by Name", notes = "Returns a reference type for a specified reference type Name.", response = ReferenceType.class, tags = {
			"ReferenceSource", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful response", response = ReferenceSource.class) })
	@RequestMapping(value = "/types/findByName/{referenceTypeName}", produces = {
			"application/json" }, method = RequestMethod.GET)
	public ResponseEntity<ReferenceType> getReferenceSourceByName(
			@ApiParam(value = "The reference type name", required = true) @PathVariable("referenceTypeName") String referenceTypeName,
			@ApiParam(value = "Correlation ID across the enterprise application components.") @RequestHeader(value = "X-Correlation-ID", required = false) String xCorrelationID,
			@ApiParam(value = "Application identifier of client.") @RequestHeader(value = "X-VV-Client-ID", required = false) String xVVClientID) {
		
		return new ResponseEntity<ReferenceType>(referenceTypesAssembly.findReferenceTypeByName(referenceTypeName),HttpStatus.OK);
	}
	/**
	 * find types by using below parameters
	 * @param page         
	 * @param size        
	 * @param xCorrelationID
	 *            - Correlation ID across the enterprise application components.
	 * @param xVVClientID
	 *            - Application identifier of client.
	 * @return List of referenceType
	 */
	@ApiOperation(value = "", notes = "Gets `ReferenceType` objects.", response = ReferenceType.class, responseContainer = "List", tags = {
			"ReferenceType", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful response", response = ReferenceType.class) })
	@RequestMapping(value = "/types", produces = { "application/json" },  method = RequestMethod.GET)
	public ResponseEntity<List<ReferenceType>> findTypes(
			@ApiParam(value = "Correlation ID across the enterprise application components.") @RequestHeader(value = "X-Correlation-ID", required = false) String xCorrelationID,
			@ApiParam(value = "Application identifier of client.") @RequestHeader(value = "X-VV-Client-ID", required = false) String xVVClientID,
			@ApiParam(value = "") @RequestParam(value = "page", required = true) Integer page,
			@ApiParam(value = "") @RequestParam(value = "size", required = true) Integer size) {
		
		return new ResponseEntity<List<ReferenceType>>(referenceTypesAssembly.findTypes(), HttpStatus.OK);
	}

	/**
	 * Update reference Type     
	 * @param  body
	 * @param xCorrelationID
	 *            - Correlation ID across the enterprise application components.
	 * @param xVVClientID
	 *            - Application identifier of client.
	 * @return ReferenceType - returns a referenceType
	 */
	@ApiOperation(value = "", notes = "Update a `ReferenceType` object.", response = Void.class, tags = {
			"ReferenceType", })
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Invalid ID supplied", response = Void.class),
			@ApiResponse(code = 404, message = "ReferenceType not found", response = Void.class),
			@ApiResponse(code = 405, message = "Validation exception", response = Void.class) })
	@RequestMapping(value = "/types", produces = { "application/json" },  method = RequestMethod.PUT)
	public ResponseEntity<ReferenceType> updateReferenceType(
			@ApiParam(value = "ReferenceType object that needs to be updated", required = true) @RequestBody ReferenceType body,
			@ApiParam(value = "Correlation ID across the enterprise application components.") @RequestHeader(value = "X-Correlation-ID", required = false) String xCorrelationID,
			@ApiParam(value = "Application identifier of client.") @RequestHeader(value = "X-VV-Client-ID", required = false) String xVVClientID) {
		
		if(StringUtils.isEmpty(body.referenceTypeID())||body.referenceType().trim().length()==0||body.referenceSourceID().trim().length()==0
				                      ||body.referenceTypeID().trim().length()==0) {
			throw new MandatoryFieldsMissingException();
		}
		ReferenceType referenceType = referenceTypesAssembly.updateReferenceType(body);
		return new ResponseEntity<ReferenceType>(referenceType,HttpStatus.OK);
	}
}
