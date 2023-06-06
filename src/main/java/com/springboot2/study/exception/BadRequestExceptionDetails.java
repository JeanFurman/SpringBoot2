package com.springboot2.study.exception;

import java.time.LocalDateTime;

public class BadRequestExceptionDetails extends ExceptionDetails{

	public BadRequestExceptionDetails(String string, int value, String message, String name, LocalDateTime now) {
		super(string, value, message, name, now);
	}
	
}
