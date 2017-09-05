package com.virginvoyages.sailor.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class to handle scenarios when data is not found for given parameters
 * @author rpraveen
 *
 */
@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Data not found for given parameters")
public class DataNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

}
