package com.virginvoyages.sailor.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class to handle scenarios when data is not found for given parameters
 * @author rpraveen
 *
 */
@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Invalid value passed in query filter")
public class InvalidQueryFilterException extends RuntimeException {

	private static final long serialVersionUID = 1L;

}
