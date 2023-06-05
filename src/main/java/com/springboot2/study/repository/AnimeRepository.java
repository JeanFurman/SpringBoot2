package com.springboot2.study.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot2.study.domain.Anime;

public interface AnimeRepository extends JpaRepository<Anime, Long>{

	
}
