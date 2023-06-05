package com.springboot2.study.requests;

public class AnimePostRequestBody {
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
