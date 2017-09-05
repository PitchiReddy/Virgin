package com.virginvoyages.sailor.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class to handle missing mandatory fields in sailor operations.
 * @author rpraveen
 *
 */
@ResponseStatus(value=HttpStatus.METHOD_NOT_ALLOWED, reason="Mandatory Fields Missing")
public class MandatoryFieldsMissingException extends RuntimeException {

	private static final long serialVersionUID = 1L;

}
