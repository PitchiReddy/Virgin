package com.virginvoyages.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_MODIFIED, reason="Data update is not done for given parameter{ID}")
public class DataUpdationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	public DataUpdationException() {
		
	}

}
