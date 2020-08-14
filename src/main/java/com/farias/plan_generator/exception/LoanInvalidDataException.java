package com.farias.plan_generator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Please, inform a valid loan! Data must not be null or less than ONE")
public class LoanInvalidDataException extends Exception{
	private static final long serialVersionUID = 1L;
}
