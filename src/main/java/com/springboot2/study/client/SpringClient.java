package com.springboot2.study.client;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.springboot2.study.domain.Anime;

public class SpringClient {

	public static void main(String[] args) {
		ResponseEntity<Anime> entity = new RestTemplate().getForEntity("htpp://localhost:8080/animes/2", Anime.class);
		
		Anime object = new RestTemplate().getForObject("htpp://localhost:8080/animes/{id}", Anime.class, 2);
		
		Anime[] animes = new RestTemplate().getForObject("htpp://localhost:8080/animes/all", Anime[].class);

		ResponseEntity<List<Anime>> exchange = new RestTemplate().exchange("htpp://localhost:8080/animes/all", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Anime>>() {});
		
		Anime kingdom = new Anime("kingdom");
		Anime kingdomSaved = new RestTemplate().postForObject("htpp://localhost:8080/animes/", kingdom, Anime.class);
		
		Anime samuraiChamploo = new Anime("Samurai Champloo");
		ResponseEntity<Anime> samuraiChamplooSaved = new RestTemplate().exchange("htpp://localhost:8080/animes/",
				HttpMethod.POST,
				new HttpEntity<>(samuraiChamploo),
				Anime.class);
		
	}
	
}
