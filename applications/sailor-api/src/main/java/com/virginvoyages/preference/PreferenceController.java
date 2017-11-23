package com.virginvoyages.preference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.virginvoyages.assembly.PreferenceAssembly;
import com.virginvoyages.model.Page;
import com.virginvoyages.preference.model.Preferences;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "Preferences", description = "Preferences for a Sailor", tags = "Preferences")
public class PreferenceController {

	@Autowired
	private PreferenceAssembly preferenceAssembly;

	@ApiOperation(value = "", notes = "", response = Preferences.class, tags = { "Preferences", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Get the preferences for this Sailor", response = Preferences.class) })
	@RequestMapping(value = "/sailors/{sailorID}/preferences", method = RequestMethod.GET)
	public ResponseEntity<Preferences> findPreferencesBySailor(
			@ApiParam(value = "The sailor identifier number", required = true) @PathVariable("sailorID") String sailorID,
			@ApiParam(value = "Correlation ID across the enterprise application components.") @RequestHeader(value = "X-Correlation-ID", required = false) String xCorrelationID,
			@ApiParam(value = "Application identifier of client.") @RequestHeader(value = "X-VV-Client-ID", required = false) String xVVClientID,
			@ApiParam(value = "") @RequestParam(value = "page", required = false) Integer page,
			@ApiParam(value = "") @RequestParam(value = "size", required = false) Integer size) {

		
		Preferences preferences = new Preferences().page(new Page().size(size))
				                          .embedded(preferenceAssembly.findSailorPreferences(sailorID));
		return new ResponseEntity<Preferences>(preferences, HttpStatus.OK);
	}

}
