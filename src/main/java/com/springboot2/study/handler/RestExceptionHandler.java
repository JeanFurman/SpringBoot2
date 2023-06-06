package com.springboot2.study.handler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.springboot2.study.exception.BadRequestException;
import com.springboot2.study.exception.BadRequestExceptionDetails;
import com.springboot2.study.exception.ValidationExceptionDetails;


@ControllerAdvice
public class RestExceptionHandler{
	
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<BadRequestExceptionDetails> handlerBadRequestException(BadRequestException bre){
		return new ResponseEntity<>(
			new BadRequestExceptionDetails("Bad Request Exception, Check the Documentation", 
					HttpStatus.BAD_REQUEST.value(),
					bre.getMessage(), bre.getClass().getName(), LocalDateTime.now()), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationExceptionDetails> handlerMethodArgumentNotValidException(
			MethodArgumentNotValidException exception){
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));
		String fieldsMessage = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));
		return new ResponseEntity<>(
			new ValidationExceptionDetails("Bad Request Exception, Invalid Fields", 
					HttpStatus.BAD_REQUEST.value(),
					exception.getMessage(), exception.getClass().getName(), LocalDateTime.now(), fields, fieldsMessage), HttpStatus.BAD_REQUEST);
	}
}
