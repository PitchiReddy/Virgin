package com.virginvoyages.recommendations.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class RecommendationExceptionHandler {
	
	@ExceptionHandler(DataInsertionException.class)
	public ResponseEntity<Void> handleDataInsertionException() {
		return new ResponseEntity<Void>(HttpStatus.NOT_MODIFIED);
	}

	@ExceptionHandler(TribeNotFoundException.class)
	public ResponseEntity<Void> handleTribeNotFoundException() {
		return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
	}


	@ExceptionHandler(InvalidSailorIdException.class)
	public ResponseEntity<Void> handleInvalidSailorIdException() {
		return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
