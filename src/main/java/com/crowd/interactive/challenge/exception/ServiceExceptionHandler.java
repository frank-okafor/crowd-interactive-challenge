package com.crowd.interactive.challenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.crowd.interactive.challenge.pojos.ServiceResponse;

@ResponseBody
@RestControllerAdvice
public class ServiceExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(value = HttpStatus.EXPECTATION_FAILED)
    public ServiceResponse nullException(NullPointerException e) {
	return new ServiceResponse(e.getMessage());
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ServiceResponse userServiceException(ServiceException e) {
	return new ServiceResponse(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ServiceResponse illegalArgumentException(IllegalArgumentException e) {
	return new ServiceResponse(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ServiceResponse generalException(Exception e) {
	return new ServiceResponse(e.getMessage());
    }

}
