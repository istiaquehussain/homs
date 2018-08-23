package com.coe.homs.util;

import java.time.LocalDateTime;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.coe.homs.entity.Fault;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	
	
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<Fault> handleCustomException(CustomException exception, WebRequest request)
	{
		Fault fault= Fault.builder()
				.message(exception.getMessage())
				.status(exception.getStatus())
				.error(exception.getStatus().getReasonPhrase())
				.timestamp(exception.getTimestamp())
				.debugMessage(request.getDescription(false))
				.build();
		
		return new ResponseEntity<Fault>(fault,exception.getStatus());
		
	}
	
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Fault> handleAllException(Exception exception, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request)
	{
		Fault fault= Fault.builder()
				.message(exception.getLocalizedMessage())
				.status(status)
				.error(status.getReasonPhrase())
				.timestamp(LocalDateTime.now())
				.debugMessage(request.getDescription(false))
				.build();
		
		return new ResponseEntity<Fault>(fault,status);
		
	}

}
