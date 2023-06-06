package com.springboot2.study.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.springboot2.study.domain.Anime;
import com.springboot2.study.exception.BadRequestException;
import com.springboot2.study.mapper.AnimeMapper;
import com.springboot2.study.repository.AnimeRepository;
import com.springboot2.study.requests.AnimePostRequestBody;
import com.springboot2.study.requests.AnimePutRequestBody;

import jakarta.transaction.Transactional;

@Service
public class AnimeService {
	
	private final AnimeRepository animeRepository;
	
	public AnimeService(final AnimeRepository animeRepository) {
		this.animeRepository = animeRepository;
	}

	public List<Anime> listAll(){
		return animeRepository.findAll();
	}
	
	public List<Anime> findByName(String name){
		return animeRepository.findByName(name);
	}
	
	public Anime findByIdOrThrowBadRequestException(long id){
		return animeRepository.findById(id)
				.orElseThrow(() -> new BadRequestException("Anime not found"));
	}

	@Transactional
	public Anime save(AnimePostRequestBody animePostRequestBody) {
		return animeRepository.save(AnimeMapper.INSTANCE.toAnime(animePostRequestBody));
	}

	public void delete(long id) {
		animeRepository.delete(findByIdOrThrowBadRequestException(id));
	}

	public void replace(AnimePutRequestBody animePutRequestBody) {
		Anime savedAnime = findByIdOrThrowBadRequestException(animePutRequestBody.getId());
		Anime anime = AnimeMapper.INSTANCE.toAnime(animePutRequestBody);
		anime.setId(savedAnime.getId());
		animeRepository.save(anime);
	}

}
