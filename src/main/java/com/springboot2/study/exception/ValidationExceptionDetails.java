package com.springboot2.study.exception;

import java.time.LocalDateTime;

public class ValidationExceptionDetails extends ExceptionDetails{

	private final String fields;
	private final String fieldsMessage;
	
	public ValidationExceptionDetails(String string, int value, String message, String name, LocalDateTime now, String fields, String fieldsMessage) {
		super(string, value, message, name, now);
		this.fields = fields;
		this.fieldsMessage = fieldsMessage;
	}

	public String getFields() {
		return fields;
	}

	public String getFieldsMessage() {
		return fieldsMessage;
	}
	
	
	
}
