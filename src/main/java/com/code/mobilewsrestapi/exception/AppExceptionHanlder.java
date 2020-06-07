package com.code.mobilewsrestapi.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.text.DefaultEditorKit.CutAction;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.CollectionUtils;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.code.mobilewsrestapi.exception.user.UserServiceException;
import com.code.mobilewsrestapi.exception.user.UserServiceNotFoundException;
import com.code.mobilewsrestapi.ui.model.response.error.CustomErrorDetails;
import com.code.mobilewsrestapi.ui.model.response.error.ErrorMessages;


@ControllerAdvice
public class AppExceptionHanlder extends ResponseEntityExceptionHandler {
	
	Logger logger = LoggerFactory.getLogger(AppExceptionHanlder.class);

	
	
	/**
	 * Custom User Service Exceptions for all exception
	 */
	@ExceptionHandler(UserServiceException.class)
	public ResponseEntity<Object> handleUserServiceException(UserServiceException ex,
		 WebRequest request) {
		
		logger.info("Inside handleUserServiceException( )");

		CustomErrorDetails details = new CustomErrorDetails(HttpStatus.BAD_REQUEST);
		details.setMessage(ErrorMessages.RECORD_ALREDAY_EXITS.getErrorMessage());

		details.setErrorMessage(ex.getMessage());
		return buildErrorResponseEntity(details);
	}
	

	
	/**
	 * Handle MethodArgumentNotValidException. Triggered when an object fails @Valid
	 * validation.
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		//logger.info("Inside handleMethodArgumentNotValid( )");

		CustomErrorDetails details = new CustomErrorDetails(HttpStatus.BAD_REQUEST);
		details.setMessage(ErrorMessages.VALIDATION_FAILED.getErrorMessage());
		details.setSubErrors(getSubErrorMap(ex));

		return buildErrorResponseEntity(details);
	}
	
	private Map<String, String> getSubErrorMap(MethodArgumentNotValidException ex) {
		Map<String, String> map = new HashMap<>();

		ex.getBindingResult().getFieldErrors().forEach(error -> {
			map.put(error.getField(), error.getDefaultMessage());
		});
		return map;

	}

	
	/**
	 * Customize the response for HttpRequestMethodNotSupportedException.
	 * <p>This method logs a warning, sets the "Allow" header, and delegates to
	 */
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
			HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
	

        CustomErrorDetails apiError = new CustomErrorDetails(HttpStatus.METHOD_NOT_ALLOWED);
        apiError.setMessage(ErrorMessages.INVALID_HTTP_METHOD.getErrorMessage());
       
        apiError.setErrorMessage(ex.getLocalizedMessage());
        return buildErrorResponseEntity(apiError);
	
	}
	
	
	/**
	 * Handle MissingServletRequestParameterException. Triggered when a 'required'
	 * request parameter is missing.
	 */
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		logger.info("Inside handleMissingServletRequestParameter( )");

		CustomErrorDetails details = new CustomErrorDetails(HttpStatus.BAD_REQUEST);
		details.setMessage(ErrorMessages.MISSING_REQUIRED_PARMETER.getErrorMessage());

		Map<String, String> map = new HashMap<>();

		map.put(ex.getParameterName(), "Parameter is missing");
		details.setSubErrors(map);
		return buildErrorResponseEntity(details);
	}

	
	
	   /**
     * Handle HttpMediaTypeNotSupportedException. This one triggers when JSON is invalid as well.
   
     */
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
    	logger.info("Inside handleHttpMediaTypeNotSupported( )");

    	
    	
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
        return buildErrorResponseEntity(new CustomErrorDetails(HttpStatus.UNSUPPORTED_MEDIA_TYPE,
        		builder.substring(0, builder.length() - 2), ex));
    }

    
   
    /**
     * Handles javax.validation.ConstraintViolationException. Thrown when @Validated fails.
     */
    @ExceptionHandler(javax.validation.ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolation(
            javax.validation.ConstraintViolationException ex) {
    	
    	logger.info("Inside handleConstraintViolation( )");

        CustomErrorDetails apiError = new CustomErrorDetails(HttpStatus.BAD_REQUEST);
        apiError.setMessage(ErrorMessages.VALIDATION_FAILED.getErrorMessage());
       
        apiError.setErrorMessage(ex.getMessage());
        return buildErrorResponseEntity(apiError);
    }
    

    /**
     * Handle HttpMessageNotReadableException. Happens when request JSON is malformed.
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, 
    		HttpHeaders headers, HttpStatus status, WebRequest request) {
        
    	 logger.info("Inside handleHttpMessageNotReadable( )");
        String error = ErrorMessages.MALFORMED_JAOSN_REQUEST.getErrorMessage();
      
        return buildErrorResponseEntity(new CustomErrorDetails(HttpStatus.BAD_REQUEST, error, ex));
    }

    /**
     * Handle HttpMessageNotWritableException.
   
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "Error writing JSON output";
        logger.info("Inside handleHttpMessageNotWritable( )");
        return buildErrorResponseEntity(new CustomErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR, error, ex));
    }

    /**
     * Handle NoHandlerFoundException.
     */
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    
    	
    	logger.info("Inside handleNoHandlerFoundException( )");
    	
    	CustomErrorDetails apiError = new CustomErrorDetails(HttpStatus.BAD_REQUEST);
        apiError.setMessage(String.format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL()));
        apiError.setErrorMessage(ex.getMessage());
        
        return buildErrorResponseEntity(apiError);
    }
    

    /**
     * Handle javax.persistence.EntityNotFoundException
     */
    @ExceptionHandler(javax.persistence.EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(javax.persistence.EntityNotFoundException ex, WebRequest request) {

    	logger.info("Inside javax.persistence.EntityNotFoundException( )");
    	
    	CustomErrorDetails details = new CustomErrorDetails(HttpStatus.NOT_FOUND);
		details.setMessage(ex.getMessage());
		details.setErrorMessage(request.getDescription(false));
		return buildErrorResponseEntity(details);
      
    }

    
   
    /**
     * Handle DataIntegrityViolationException, inspects the cause for different DB causes
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex,
                                                                  WebRequest request) {
    	
    	logger.info("Inside handleDataIntegrityViolation( )"+ex.getMessage());
    	
        if (ex.getCause() instanceof ConstraintViolationException) {
            return buildErrorResponseEntity(new CustomErrorDetails(HttpStatus.CONFLICT, "Database error", ex.getCause()));
        }
        return buildErrorResponseEntity(new CustomErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR, ex));
    }

    /**
     * Handle Exception, handle generic Exception.class
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                                      WebRequest request) {
        CustomErrorDetails apiError = new CustomErrorDetails(HttpStatus.BAD_REQUEST);
        

    	logger.info("Inside handleMethodArgumentTypeMismatch( )");
        apiError.setMessage(String.format("The parameter '%s' of value '%s' could not be converted to type '%s'",
        		ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName()));
        apiError.setMessage(ex.getMessage());
        return buildErrorResponseEntity(apiError);
    }

	
	
	
	private ResponseEntity<Object> buildErrorResponseEntity(CustomErrorDetails details) {
		return new ResponseEntity<>(details, details.getStatus());
	}
}
