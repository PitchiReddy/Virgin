package com.virginvoyages.crossreference.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.METHOD_NOT_ALLOWED, reason="id is foreing key in child table")
public class DataAccessException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    public DataAccessException() {

    }
}
