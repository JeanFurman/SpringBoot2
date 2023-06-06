package com.springboot2.study.handler;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.springboot2.study.exception.BadRequestException;
import com.springboot2.study.exception.BadRequestExceptionDetails;

@ControllerAdvice
public class RestExceptionHandler {
	
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<BadRequestExceptionDetails> handlerBadRequestException(BadRequestException bre){
		return new ResponseEntity<>(
			new BadRequestExceptionDetails("Bad Request Exception, Check the Documentation", 
					HttpStatus.BAD_REQUEST.value(),
					bre.getMessage(), bre.getClass().getName(), LocalDateTime.now()), HttpStatus.BAD_REQUEST);
	}
	
}
