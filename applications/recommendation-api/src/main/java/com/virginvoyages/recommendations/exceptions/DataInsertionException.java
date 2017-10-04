package com.virginvoyages.recommendations.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_MODIFIED, reason="Data Insert Failed")
public class DataInsertionException extends RuntimeException{

	private static final long serialVersionUID = 1L;
}
