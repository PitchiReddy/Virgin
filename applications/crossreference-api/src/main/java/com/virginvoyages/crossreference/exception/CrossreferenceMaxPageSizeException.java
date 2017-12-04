package com.virginvoyages.crossreference.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.METHOD_NOT_ALLOWED, reason="Page size exceeds maximum value of 20")
public class CrossreferenceMaxPageSizeException  extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    public  CrossreferenceMaxPageSizeException() {
    	
    }
}
