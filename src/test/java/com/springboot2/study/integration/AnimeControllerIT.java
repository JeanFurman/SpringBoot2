package com.springboot2.study.integration;

import java.util.Collections;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import com.springboot2.study.domain.Anime;
import com.springboot2.study.repository.AnimeRepository;
import com.springboot2.study.requests.AnimePostRequestBody;
import com.springboot2.study.util.AnimeCreator;
import com.springboot2.study.util.AnimePostRequestBodyCreator;
import com.springboot2.study.util.AnimePutRequestBodyCreator;
import com.springboot2.study.wrapper.PageableResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AnimeControllerIT {
	
	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private AnimeRepository animeRepository;
	
	@Test
	@DisplayName("List returns list of anime inside page object when sucessful")
	void list_ReturnsListOfAnimesInsidePageObject_WhenSucessful() {
		
		Anime animeSaved = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
		
		String expectedName = animeSaved.getName();
		
		Pa
		geableResponse<Anime> animePage = testRestTemplate.exchange("/animes",
				HttpMethod.GET, null, new ParameterizedTypeReference<PageableResponse<Anime>>() {
				}).getBody();
		
		Assertions.assertThat(animePage).isNotNull();
		Assertions.assertThat(animePage.toList()).isNotEmpty().hasSize(1);
		Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
		
	}
	
	@Test
	@DisplayName("List returns list of animes when sucessful")
	void listAll_ReturnsListOfAnimes_WhenSucessful() {

		Anime animeSaved = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
		
		String expectedName = animeSaved.getName();
		
		List<Anime> animes = testRestTemplate.exchange("/animes/all",
				HttpMethod.GET, null, new ParameterizedTypeReference<List<Anime>>() {
				}).getBody();
		
		Assertions.assertThat(animes).isNotNull().isNotEmpty().hasSize(1);
		Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
		
	}
	
	@Test
	@DisplayName("findById returns anime when sucessful")
	void findById_ReturnsAnime_WhenSucessful() {
		
		Anime animeSaved = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
		
		Long expectedId = animeSaved.getId();
		
		Anime anime = testRestTemplate.getForObject("/animes/{id}", Anime.class, expectedId);
		
		Assertions.assertThat(anime).isNotNull();
		Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(expectedId);
		
	}
	
	@Test
	@DisplayName("findByName returns a list of animes when sucessful")
	void findByName_ReturnsListOfAnime_WhenSucessful() {
		
		Anime animeSaved = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
		
		String expectedName = animeSaved.getName();
		String url = String.format("/animes/find?name=%s", expectedName);
				
		List<Anime> animes = testRestTemplate.exchange(url,
				HttpMethod.GET, null, new ParameterizedTypeReference<List<Anime>>() {
				}).getBody();
		
		Assertions.assertThat(animes).isNotNull().isNotEmpty().hasSize(1);
		Assertions.assertThat(animes.get(0).getName()).isNotNull().isEqualTo(AnimeCreator.createValidAnime().getName());
		
	}
	
	@Test
	@DisplayName("findByName returns an empty list of animes when anime is not found")
	void findByName_ReturnsEmptyListOfAnime_WhenAnimeIsNotFound() {
						
		List<Anime> animes = testRestTemplate.exchange("/animes/find?name=dbz",
				HttpMethod.GET, null, new ParameterizedTypeReference<List<Anime>>() {
				}).getBody();
		
		Assertions.assertThat(animes).isNotNull().isEmpty();

	}
	
	@Test
	@DisplayName("save returns anime when sucessful")
	void save_ReturnsAnime_WhenSucessful() {
		
		AnimePostRequestBody animePostRequestBody = AnimePostRequestBodyCreator.createAnimePostRequestBody();
		
		ResponseEntity<Anime> animeResponseEntity = testRestTemplate.postForEntity("/animes", animePostRequestBody, Anime.class);
		
		Assertions.assertThat(animeResponseEntity).isNotNull();
		Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		Assertions.assertThat(animeResponseEntity.getBody()).isNotNull();
		Assertions.assertThat(animeResponseEntity.getBody().getId()).isNotNull();
		
	}
	
	@Test
	@DisplayName("replace updates anime when sucessful")
	void replace_UpdatesAnime_WhenSucessful() {
		
		Anime animeSaved = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
		
		animeSaved.setName("new name");
		
		ResponseEntity<Void> animeResponseEntity = testRestTemplate.exchange("/animes", 
				HttpMethod.PUT, new HttpEntity<>(animeSaved), Void.class);
		
		Assertions.assertThat(animeResponseEntity).isNotNull();
		Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
		
	}
	
	@Test
	@DisplayName("delete removes anime when sucessful")
	void delete_RemovesAnime_WhenSucessful() {
		
		Anime animeSaved = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
		
		ResponseEntity<Void> animeResponseEntity = testRestTemplate.exchange("/animes/{id}", 
				HttpMethod.DELETE, null, Void.class, animeSaved.getId());
		
		Assertions.assertThat(animeResponseEntity).isNotNull();
		Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
		
	}
	
	
}