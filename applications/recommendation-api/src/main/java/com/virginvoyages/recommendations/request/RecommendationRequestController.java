package com.virginvoyages.recommendations.request;

import javax.validation.constraints.NotNull;

import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.virginvoyages.recommendations.model.Recommendation;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Api(value = "Recommendation", tags = "Recommendation", description = "Recommendation request")
@Slf4j
@RequiredArgsConstructor
@ExposesResourceFor(Recommendation.class)
public class RecommendationRequestController {

	@ApiOperation(value = "", notes = "To get back with the Recommendataion attached to the nbxUnique ", response = Recommendation.class, tags = {
			"Request Recommendation", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful response", response = Recommendation.class) })
	@RequestMapping(value = "/recommendationRequest", method = RequestMethod.GET)
	public ResponseEntity<Recommendation> recommendationRequestGet(
			@NotNull @ApiParam(value = "the unique key associated with each recommendation when it is issued", required = true) @RequestParam(value = "nbxUniqueKey", required = true) Integer nbxUniqueKey) {
		log.debug("Entering recommendationRequestGet");
		return new ResponseEntity<Recommendation>(HttpStatus.OK);
	}

}
