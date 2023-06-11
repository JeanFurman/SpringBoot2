package com.springboot2.study.domain;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class StudyUser implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "The anime name can't be empty")
	private String name;
	
	private String username;
	private String password;
	private String authorities;

	public StudyUser() {
		super();
	}

	public StudyUser(String name) {
		super();
		this.name = name;
	}

	public StudyUser(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public StudyUser(@NotEmpty(message = "The anime name can't be empty") String name, String username, String password,
			String authorities) {
		super();
		this.name = name;
		this.username = username;
		this.password = password;
		this.authorities = authorities;
	}

	public StudyUser(Long id, @NotEmpty(message = "The user's name can't be empty") String name, String username,
			String password, String authorities) {
		super();
		this.id = id;
		this.name = name;
		this.username = username;
		this.password = password;
		this.authorities = authorities;
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

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAuthorities(String authorities) {
		this.authorities = authorities;
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
		StudyUser other = (StudyUser) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Anime [id=" + id + ", name=" + name + "]";
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.stream(authorities.split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
