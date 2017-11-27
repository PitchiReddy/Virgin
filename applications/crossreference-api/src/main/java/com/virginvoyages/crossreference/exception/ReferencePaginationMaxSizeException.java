package com.virginvoyages.crossreference.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.METHOD_NOT_ALLOWED, reason="Reference Pagination Size exceeds Max Size validation ")
public class ReferencePaginationMaxSizeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    public  ReferencePaginationMaxSizeException() {
    	
    }
}
