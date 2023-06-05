package com.springboot2.study.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot2.study.domain.Anime;
import com.springboot2.study.service.AnimeService;
import com.springboot2.study.util.DateUtil;

@RestController
@RequestMapping("animes")
public class AnimeController {
	
	private DateUtil dateUtil;
	private AnimeService animeService;

	public AnimeController(DateUtil dateUtil, AnimeService animeService) {
		super();
		this.dateUtil = dateUtil;
		this.animeService = animeService;
	}


	@GetMapping
	public ResponseEntity<List<Anime>> list(){
		return new ResponseEntity<>(animeService.listAll(), HttpStatus.OK);
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<Anime> findById(@PathVariable long id){
		return ResponseEntity.ok(animeService.findById(id));
	}
	
}
