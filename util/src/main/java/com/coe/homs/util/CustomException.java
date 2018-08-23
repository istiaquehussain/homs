package com.coe.homs.util;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public class CustomException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LocalDateTime timestamp;
	private HttpStatus status;
	private String message="NA";
	private String debugMessage="NA";
	
	public CustomException()
	{
		super();
		timestamp = LocalDateTime.now();
	}
	public CustomException(HttpStatus status)
	{
		this();
		this.status = status;
	}
	
	public CustomException(HttpStatus status, String message) {
	       this();
	       this.status = status;
	       this.message = message;
	   }
	
	public CustomException(HttpStatus status, Throwable ex) {
	       this();
	       this.status = status;
	       this.message = "Unexpected error";
	       this.debugMessage = ex.getLocalizedMessage();
	   }
	
	public CustomException(HttpStatus status, String message, Throwable ex) {
	       this();
	       this.status = status;
	       this.message = message;
	       this.debugMessage = ex.getLocalizedMessage();
	   }
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDebugMessage() {
		return debugMessage;
	}
	public void setDebugMessage(String debugMessage) {
		this.debugMessage = debugMessage;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	

}
