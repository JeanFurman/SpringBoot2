package com.springboot2.study.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springboot2.study.repository.StudyUserRepository;

@Service
public class StudyUserDetailsService implements UserDetailsService{

	private final StudyUserRepository studyUserRepository;
	
	public StudyUserDetailsService(final StudyUserRepository studyUserRepository) {
		this.studyUserRepository = studyUserRepository;
	}



	@Override
	public UserDetails loadUserByUsername(String username){
		return Optional.ofNullable(studyUserRepository.findByUsername(username))
				.orElseThrow(() -> new UsernameNotFoundException("Study User not found."));
	}

}
