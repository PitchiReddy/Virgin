package com.virginvoyages.sailor.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class to handle account creation exception
 * @author rpraveen
 *
 */
@ResponseStatus(value=HttpStatus.BAD_GATEWAY, reason="Operation failed due to unknown reason.")
public class UnknownException extends RuntimeException {

	private static final long serialVersionUID = 1L;

}
