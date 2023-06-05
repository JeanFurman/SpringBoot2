package com.springboot2.study.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.springboot2.study.domain.Anime;

@Service
public class AnimeService {
	
	private static List<Anime> animes; 
		
	static 
	{
		animes = new ArrayList<>(List.of(new Anime(1L, "Boku no hero"), new Anime(2L, "Berserk")));
	}
	
	
	public List<Anime> listAll(){
		return animes;
	}
	
	public Anime findById(long id){
		return animes.stream()
				.filter(anime -> anime.getId().equals(id))
				.findFirst()
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Anime not found"));
	}

	public Anime save(Anime anime) {
		anime.setId(ThreadLocalRandom.current().nextLong(3, 100000));
		animes.add(anime);
		return anime;
	}

}
