package com.springboot2.study.requests;

import jakarta.validation.constraints.NotEmpty;

public class AnimePostRequestBody {
	
	@NotEmpty(message = "The anime name can't be empty")
	private String name;

	public AnimePostRequestBody() {
	}

	public AnimePostRequestBody(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "AnimePostRequestBody [name=" + name + "]";
	}
	
	
}
