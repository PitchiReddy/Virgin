package com.virginvoyages.crossreference.references;


import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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
import com.virginvoyages.crossreference.api.MockCrossReferenceAPI;
import com.virginvoyages.crossreference.assembly.ReferencesAssembly;
import com.virginvoyages.crossreference.exception.ReferenceIDMaxRequestSizeException;
import com.virginvoyages.exception.DataInsertionException;
import com.virginvoyages.exception.DataNotFoundException;
import com.virginvoyages.exception.DataUpdationException;
import com.virginvoyages.exception.MandatoryFieldsMissingException;
import com.virginvoyages.model.Page;
import com.virginvoyages.crossreference.model.Reference;
import com.virginvoyages.crossreference.model.References;
import com.virginvoyages.crossreference.model.ReferencesEmbedded;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller class to handle API requests for operations related to
 * ReferenceTypes.
 * 
 * @author snarthu
 *
 */
@RestController
@Api(value = "References", tags = "References", description = "References operations")
@Slf4j
@RequiredArgsConstructor
@ExposesResourceFor(References.class)
public class ReferencesController {
	
	@Autowired
	private ReferencesAssembly referencesAssembly; 
	
	
	@Autowired
	private MockCrossReferenceAPI mockAPI; 
	
	
	/**
	 * It is adding new Reference
	 * 
	 * @param Reference
	 * @param xCorrelationID
	 *            - Correlation ID across the enterprise application components.
	 * @param xVVClientID
	 *            - Application identifier of client.
	 * @return Reference -  returns a reference
	 */
	@ApiOperation(value = "", notes = "Add a new `Reference`.", response = Void.class, tags = { "Reference", })
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Created", response = Void.class),
			@ApiResponse(code = 405, message = "Invalid input", response = Void.class) })
	@RequestMapping(value = "/references", produces = { "application/json" },  method = RequestMethod.POST)
	public ResponseEntity<Reference> addReference(
			@ApiParam(value = "Reference object that needs to be created", required = true) @RequestBody Reference body,
			@ApiParam(value = "Correlation ID across the enterprise application components.") @RequestHeader(value = "X-Correlation-ID", required = false) String xCorrelationID,
			@ApiParam(value = "Application identifier of client.") @RequestHeader(value = "X-VV-Client-ID", required = false) String xVVClientID) {
		
		log.debug("Adding Reference");
		if(StringUtils.isBlank(body.referenceTypeID())||StringUtils.isBlank(body.nativeSourceIDValue())) {
			throw new MandatoryFieldsMissingException();
		}
		Reference reference = referencesAssembly.addReference(body);
		if(null == reference) {
			log.error("Reference Not saved due to unknown reasons ==> "+body.referenceTypeID());
			throw new DataInsertionException("Reference Not Saved due to Unkown reasons");
		}
		return new ResponseEntity<Reference>(reference,HttpStatus.OK);
	}

	/**
	 * @param referenceID
	 *            - Find reference by ID
	 * @param xCorrelationID
	 *            - Correlation ID across the enterprise application components.
	 * @param xVVClientID
	 *            - Application identifier of client.
	 * @return
	 */
	@ApiOperation(value = "", notes = "Remove the Reference", response = Void.class, tags = { "Reference", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful response", response = Void.class),
			@ApiResponse(code = 404, message = "Reference not found", response = Void.class) })
	@RequestMapping(value = "/references/{referenceID}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteReferenceByID(
			@ApiParam(value = "The reference identifier", required = true) @PathVariable("referenceID") String referenceID,
			@ApiParam(value = "Correlation ID across the enterprise application components.") @RequestHeader(value = "X-Correlation-ID", required = false) String xCorrelationID,
			@ApiParam(value = "Application identifier of client.") @RequestHeader(value = "X-VV-Client-ID", required = false) String xVVClientID) {
		

		if(StringUtils.isBlank(referenceID)) {
			throw new MandatoryFieldsMissingException();
		}
		referencesAssembly.deleteReferenceByID(referenceID);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	/**
	 * @param referenceID
	 *            - Find reference  by ID
	 * @param xCorrelationID
	 *            - Correlation ID across the enterprise application components.
	 * @param xVVClientID
	 *            - Application identifier of client.
	 * @return Reference - returns a reference
	 * @throws MandatoryFieldsMissingException 
	 */
	@ApiOperation(value = "Find reference by ID", notes = "Returns a reference for a specified reference identity.  This identity is a univeral reference identity.", response = Reference.class, tags = {
			"Reference", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful response", response = Reference.class) })
	@RequestMapping(value = "/references/{referenceID}", produces = { "application/json" }, method = RequestMethod.GET)
	public ResponseEntity<Reference> findReferenceByID(
			@ApiParam(value = "The reference identifier", required = true) @PathVariable("referenceID") String referenceID,
			@ApiParam(value = "Correlation ID across the enterprise application components.") @RequestHeader(value = "X-Correlation-ID", required = false) String xCorrelationID,
			@ApiParam(value = "Application identifier of client.") @RequestHeader(value = "X-VV-Client-ID", required = false) String xVVClientID) throws MandatoryFieldsMissingException {
	
		log.debug("Find reference by ID");
		if (StringUtils.isBlank(referenceID))
			throw new MandatoryFieldsMissingException();
		if (referenceID.trim().length() >= 33) {
			throw new ReferenceIDMaxRequestSizeException();
		}
		Reference reference = referencesAssembly.findReferenceByID(referenceID);
		if (null == reference) {
			throw new DataNotFoundException();
		}
		return new ResponseEntity<Reference>(reference, HttpStatus.OK);

	}

	/**
	 * Gets `Reference` objects
	 * @param page         
	 * @param size        
	 * @param xCorrelationID
	 *            - Correlation ID across the enterprise application components.
	 * @param xVVClientID
	 *            - Application identifier of client.
	 * @return References
	 */
	@ApiOperation(value = "", notes = "Gets `Reference` objects.", response = References.class, tags = { "Reference", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful response", response = References.class) })
	@RequestMapping(value = "/references", produces = { "application/json" },  method = RequestMethod.GET)
	public ResponseEntity<References> findReferences(
			@ApiParam(value = "Correlation ID across the enterprise application components.") @RequestHeader(value = "X-Correlation-ID", required = false) String xCorrelationID,
			@ApiParam(value = "Application identifier of client.") @RequestHeader(value = "X-VV-Client-ID", required = false) String xVVClientID,
			@ApiParam(value = "") @RequestParam(value = "page", required = true) Integer page,
			@ApiParam(value = "") @RequestParam(value = "size", required = true) Integer size,
			final Pageable pageable) {
		
		log.debug("Find reference objects");
		if(size == 0) {
			throw new MandatoryFieldsMissingException();
		}
		List<Reference> referenceList = referencesAssembly.findReferences(pageable);
		return new ResponseEntity<References>(new References().page(new Page().size(pageable.getPageSize()).number(pageable.getPageNumber()))
				.embedded(new ReferencesEmbedded().references(referenceList)), HttpStatus.OK);
	}
	
	/**
	 * @param masterID
	 *            - Find reference type by masterID
	 * @param xCorrelationID
	 *            - Correlation ID across the enterprise application components.
	 * @param xVVClientID
	 *            - Application identifier of client.
	 * @return List ofReference - returns a referenceList
	 */

	@ApiOperation(value = "", notes = "Returns one or more references", response = Reference.class, responseContainer = "List", tags = {
			"Reference", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful response", response = Reference.class) })
	@RequestMapping(value = "/references/search/findByMaster", method = RequestMethod.GET)
	public ResponseEntity<References> findReferencesMaster(
			@ApiParam(value = "The master ID to search with.", required = true) @RequestParam(value = "masterID", required = true) String masterID,
			@ApiParam(value = "Correlation ID across the enterprise application components.") @RequestHeader(value = "X-Correlation-ID", required = false) String xCorrelationID,
			@ApiParam(value = "Application identifier of client.") @RequestHeader(value = "X-VV-Client-ID", required = false) String xVVClientID,
			@ApiParam(value = "The optional target type identifier.  Supplying this narrows the results to return only the matching target type.") @RequestParam(value = "targetTypeID", required = false) String targetTypeID,
		    final Pageable pageable ) {
	
		List<Reference> listOfReference = referencesAssembly.findReferenceByMasterId(masterID,targetTypeID,pageable);
		log.debug("Returns one or more references ====>{}",listOfReference);
		References references = new References().embedded(new ReferencesEmbedded().references(listOfReference));
		return new ResponseEntity<References>(references,HttpStatus.OK);
	}

	@ApiOperation(value = "", notes = "Returns one or more references", response = Reference.class, responseContainer = "List", tags = {
			"Reference", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful response", response = Reference.class) })
	@RequestMapping(value = "/references/search/findByType", method = RequestMethod.POST)
	public ResponseEntity<References> findReferencesType(
			@ApiParam(value = "Correlation ID across the enterprise application components.") @RequestHeader(value = "X-Correlation-ID", required = false) String xCorrelationID,
			@ApiParam(value = "Application identifier of client.") @RequestHeader(value = "X-VV-Client-ID", required = false) String xVVClientID,
			@ApiParam(value = "Parameters to find reference by source.") @RequestBody Reference reference ) {
		
		//TODO mandatory check for nativesourceidval and referencetypeid
		log.debug("Search params ===> "+reference.masterID()+"  "+reference.nativeSourceIDValue()+"  "+reference.referenceTypeID()+" "+reference.targetReferenceTypeID());
		List<Reference> referenceList = mockAPI.findReferencesByType(reference.nativeSourceIDValue(), reference.referenceTypeID(), reference.targetReferenceTypeID());
		References references = new References().embedded(new ReferencesEmbedded().references(referenceList));
		return new ResponseEntity<References>(references,HttpStatus.OK);
	}

	@ApiOperation(value = "", notes = "Returns one or more references", response = Reference.class, responseContainer = "List", tags = {
			"Reference", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful response", response = Reference.class) })
	@RequestMapping(value = "/references/search/findByTypeAndTargetType", method = RequestMethod.POST)
	public ResponseEntity<References> findReferencesTypeAndTargetType(
			@ApiParam(value = "Correlation ID across the enterprise application components.") @RequestHeader(value = "X-Correlation-ID", required = false) String xCorrelationID,
			@ApiParam(value = "Application identifier of client.") @RequestHeader(value = "X-VV-Client-ID", required = false) String xVVClientID,
			@ApiParam(value = "Parameters to find reference by type.") @RequestBody Reference reference) {
		
		//TODO mandatory check for nativesourceidval and referencetypeid and targetReferenceTypeID
		List<Reference> referenceList =mockAPI.findReferenceTypeAndTargetType(reference.nativeSourceIDValue(),reference.referenceTypeID(),reference.targetReferenceTypeID());
		log.debug("Search params ===> "+reference.masterID()+"  "+reference.nativeSourceIDValue()+"  "+reference.referenceTypeID()+" "+reference.targetReferenceTypeID());
		References references = new References().embedded(new ReferencesEmbedded().references(referenceList));
		return new ResponseEntity<References>(references,HttpStatus.OK);
	}

	@ApiOperation(value = "", notes = "Merge references.  SOR specific logic of deleting the duplicate record is callers responsibility.", response = Reference.class, responseContainer = "List", tags = {
			"Reference", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful response", response = Reference.class) })
	@RequestMapping(value = "/references/merge/mergeByMaster", method = RequestMethod.POST)
	public ResponseEntity<References> mergeReferencesMaster(
			@ApiParam(value = "The master ID to merge from.", required = true) @RequestParam(value = "fromMasterID", required = true) String fromMasterID,
			@ApiParam(value = "The master ID to merge towards.", required = true) @RequestParam(value = "toMasterID", required = true) String toMasterID,
			@ApiParam(value = "Correlation ID across the enterprise application components.") @RequestHeader(value = "X-Correlation-ID", required = false) String xCorrelationID,
			@ApiParam(value = "Application identifier of client.") @RequestHeader(value = "X-VV-Client-ID", required = false) String xVVClientID) {
		// do some magic!
		return new ResponseEntity<References>(HttpStatus.OK);
	}

	@ApiOperation(value = "", notes = "Merge references.  SOR specific logic of deleting the duplicate record is callers responsibility.", response = Reference.class, responseContainer = "List", tags = {
			"Reference", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful response", response = Reference.class) })
	@RequestMapping(value = "/references/merge/mergeBySource", method = RequestMethod.POST)
	public ResponseEntity<References> mergeReferencesSource(
			@ApiParam(value = "The source ID of the source.", required = true) @RequestParam(value = "sourceID", required = true) String sourceID,
			@ApiParam(value = "The native source ID to merge from.", required = true) @RequestParam(value = "fromNativeID", required = true) String fromNativeID,
			@ApiParam(value = "The native source ID to merge towards.", required = true) @RequestParam(value = "toNativeID", required = true) String toNativeID,
			@ApiParam(value = "Correlation ID across the enterprise application components.") @RequestHeader(value = "X-Correlation-ID", required = false) String xCorrelationID,
			@ApiParam(value = "Application identifier of client.") @RequestHeader(value = "X-VV-Client-ID", required = false) String xVVClientID) {
		// do some magic!
		return new ResponseEntity<References>(HttpStatus.OK);
	}
	
	/**
	 * Update reference      
	 * @param  body
	 * @param xCorrelationID
	 *            - Correlation ID across the enterprise application components.
	 * @param xVVClientID
	 *            - Application identifier of client.
	 * @return Reference - returns a reference
	 */

	@ApiOperation(value = "", notes = "Update a `Reference` object.", response = Void.class, tags = { "Reference", })
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Invalid ID supplied", response = Void.class),
			@ApiResponse(code = 404, message = "Reference not found", response = Void.class),
			@ApiResponse(code = 405, message = "Validation exception", response = Void.class) })
	@RequestMapping(value = "/references", produces = { "application/json" }, method = RequestMethod.PUT)
	public ResponseEntity<Reference> updateReference(
			@ApiParam(value = "Reference object that needs to be updated", required = true) @RequestBody Reference body,
			@ApiParam(value = "Correlation ID across the enterprise application components.") @RequestHeader(value = "X-Correlation-ID", required = false) String xCorrelationID,
			@ApiParam(value = "Application identifier of client.") @RequestHeader(value = "X-VV-Client-ID", required = false) String xVVClientID) {

		if(StringUtils.isBlank(body.referenceID()) || StringUtils.isBlank(body.referenceTypeID())
				|| StringUtils.isBlank(body.nativeSourceIDValue())) {
			throw new MandatoryFieldsMissingException();
		}
		Reference reference =	referencesAssembly.updateReference(body);
		if(null == reference) {
			log.error("Reference Not saved due to unknown reasons ==> "+body.referenceID());
			throw new DataUpdationException();
		}
		return new ResponseEntity<Reference>(reference,HttpStatus.OK);
	}
}
