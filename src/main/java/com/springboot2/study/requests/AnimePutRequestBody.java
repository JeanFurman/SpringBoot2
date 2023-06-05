package com.springboot2.study.requests;

public class AnimePutRequestBody {
	private Long id;
	private String name;

	public AnimePutRequestBody() {
	}

	public AnimePutRequestBody(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "AnimePutRequestBody [id=" + id + ", name=" + name + "]";
	}
	
}
