package com.virginvoyages.crossreference.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="id is not available for given parameters")
public class DataUpdationException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    public DataUpdationException() { 
	   
    }
}
