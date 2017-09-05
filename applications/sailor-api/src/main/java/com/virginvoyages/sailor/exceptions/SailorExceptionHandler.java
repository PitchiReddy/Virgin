package com.virginvoyages.sailor.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Exception handler class for exception thrown during Sailor operations
 * @author rpraveen
 *
 */
public class SailorExceptionHandler {
	
	@ExceptionHandler(MandatoryFieldsMissingException.class)
	public ResponseEntity<Void> handleMandatoryFieldsMissingException() {
		return new ResponseEntity<Void>(HttpStatus.METHOD_NOT_ALLOWED);
	}

	@ExceptionHandler(AccountCreationException.class)
	public ResponseEntity<Void> handleAccountCreationException() {
		return new ResponseEntity<Void>(HttpStatus.BAD_GATEWAY);
	}

	@ExceptionHandler(DataNotFoundException.class)
	public ResponseEntity<Void> handleDataNotFoundExcception() {
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(DataNotFoundException.class)
	public ResponseEntity<Void> handleInvalidQueryFilterException() {
		return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
	}
}
