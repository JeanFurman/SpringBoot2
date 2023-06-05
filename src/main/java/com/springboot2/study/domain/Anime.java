package com.springboot2.study.domain;

import java.util.Objects;

public class Anime {
	
	private Long id;
	private String name;
	
	public Anime() {
		super();
	}
	
	public Anime(Long id, String name) {
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
	public int hashCode() {
		return Objects.hash(id);
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Anime other = (Anime) obj;
		return Objects.equals(id, other.id);
	}
	
	
	@Override
	public String toString() {
		return "Anime [id=" + id + ", name=" + name + "]";
	}

	
	
}
