package com.virginvoyages.crossreference.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="ReferenceID exceeds Max length validation ")
public class ReferenceIDMaxRequestSizeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ReferenceIDMaxRequestSizeException(){
	}

}
