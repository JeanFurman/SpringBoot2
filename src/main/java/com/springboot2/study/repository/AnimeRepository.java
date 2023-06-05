package com.springboot2.study.repository;

import java.util.List;

import com.springboot2.study.domain.Anime;

public interface AnimeRepository {

	List<Anime> listAll();
	
}
