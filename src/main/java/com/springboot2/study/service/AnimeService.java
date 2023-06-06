package com.springboot2.study.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

	public Page<Anime> listAll(Pageable pageable){
		return animeRepository.findAll(pageable);
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
