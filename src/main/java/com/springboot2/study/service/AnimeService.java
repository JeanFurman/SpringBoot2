package com.springboot2.study.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.springboot2.study.domain.Anime;
import com.springboot2.study.repository.AnimeRepository;
import com.springboot2.study.requests.AnimePostRequestBody;
import com.springboot2.study.requests.AnimePutRequestBody;

@Service
public class AnimeService {
	
	private final AnimeRepository animeRepository;
	
	public AnimeService(final AnimeRepository animeRepository) {
		this.animeRepository = animeRepository;
	}

	public List<Anime> listAll(){
		return animeRepository.findAll();
	}
	
	public Anime findByIdOrThrowBadRequestException(long id){
		return animeRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Anime not found"));
	}

	public Anime save(AnimePostRequestBody animePostRequestBody) {
		Anime anime = new Anime();
		anime.setName(animePostRequestBody.getName());
		return animeRepository.save(anime);
	}

	public void delete(long id) {
		animeRepository.delete(findByIdOrThrowBadRequestException(id));
	}

	public void replace(AnimePutRequestBody animePutRequestBody) {
		Anime anime = findByIdOrThrowBadRequestException(animePutRequestBody.getId());
		anime.setName(animePutRequestBody.getName());
		animeRepository.save(anime);
	}

}
