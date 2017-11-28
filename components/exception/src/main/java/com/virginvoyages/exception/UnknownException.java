package com.virginvoyages.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR, reason="Failed Due to Unknown reason.Please contact System administrator")
public class UnknownException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UnknownException() {

	}

}
