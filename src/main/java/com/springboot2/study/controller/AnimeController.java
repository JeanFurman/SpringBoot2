package com.springboot2.study.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot2.study.domain.Anime;
import com.springboot2.study.requests.AnimePostRequestBody;
import com.springboot2.study.requests.AnimePutRequestBody;
import com.springboot2.study.service.AnimeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("animes")
public class AnimeController {
	
	private AnimeService animeService;

	public AnimeController(AnimeService animeService) {
		super();
		this.animeService = animeService;
	}
	
	@GetMapping
	public ResponseEntity<Page<Anime>> list(Pageable pageable){
		return new ResponseEntity<>(animeService.listAll(pageable), HttpStatus.OK);
	}

	@GetMapping(path = "/all")
	public ResponseEntity<List<Anime>> listAll(){
		return new ResponseEntity<>(animeService.listAllNonPageable(), HttpStatus.OK);
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<Anime> findById(@PathVariable long id){
		return ResponseEntity.ok(animeService.findByIdOrThrowBadRequestException(id));
	}
	
	@GetMapping(path = "by-id/{id}")
//	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Anime> findByIdAuthenticationPrincipal(@PathVariable long id,
																@AuthenticationPrincipal UserDetails userDetails){
		return ResponseEntity.ok(animeService.findByIdOrThrowBadRequestException(id));
	}
	
	@GetMapping(path = "/find")
	public ResponseEntity<List<Anime>> findByName(@RequestParam String name){
		return ResponseEntity.ok(animeService.findByName(name));
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Anime> save(@RequestBody @Valid AnimePostRequestBody animePostRequestBody){
		return new ResponseEntity<>(animeService.save(animePostRequestBody), HttpStatus.CREATED);
	}
	
	@DeleteMapping(path = "/admin/{id}")
	public ResponseEntity<Void> delete(@PathVariable long id){
		animeService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PutMapping
	public ResponseEntity<Void> replace(@RequestBody AnimePutRequestBody animePutRequestBody){
		animeService.replace(animePutRequestBody);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}
