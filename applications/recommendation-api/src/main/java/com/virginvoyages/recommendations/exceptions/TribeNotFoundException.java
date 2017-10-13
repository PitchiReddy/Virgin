package com.virginvoyages.recommendations.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR, reason="Tribe Not Found")
public class TribeNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

}
