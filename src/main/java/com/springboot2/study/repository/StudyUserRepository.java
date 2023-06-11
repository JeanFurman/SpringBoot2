package com.springboot2.study.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot2.study.domain.Anime;
import com.springboot2.study.domain.StudyUser;

public interface StudyUserRepository extends JpaRepository<StudyUser, Long>{

	StudyUser findByUsername(String username);
	
}
