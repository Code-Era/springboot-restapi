package com.code.mobilewsrestapi.ui.model.response.error;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;

public class CustomErrorDetails {

	private HttpStatus status;
	
	@JsonFormat(shape= JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private Date date = new Date();
	private String message;
	private String errorMessage;
	private Map<String, String> subErrors;
	
	public CustomErrorDetails(HttpStatus status) {
		this.status = status;
	}
	

	public CustomErrorDetails(HttpStatus status, Throwable ex) {
       
        this.status = status;
        this.message = "Unexpected error";
        this.errorMessage = ex.getLocalizedMessage();
    }

    public CustomErrorDetails(HttpStatus status, String message, Throwable ex) {
     
        this.status = status;
        this.message = ex.getLocalizedMessage();
        this.errorMessage = ex.getLocalizedMessage();
    }
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	public Map<String, String> getSubErrors() {
		return subErrors;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setSubErrors(Map<String, String> subErrors) {
		this.subErrors = subErrors;
	}


	public HttpStatus getStatus() {
		return status;
	}

	public Date getDate() {
		return date;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}


	public String getErrorMessage() {
		return errorMessage;
	}


	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
