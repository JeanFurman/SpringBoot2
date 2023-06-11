package com.springboot2.study.integration;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import com.springboot2.study.domain.Anime;
import com.springboot2.study.domain.StudyUser;
import com.springboot2.study.repository.AnimeRepository;
import com.springboot2.study.repository.StudyUserRepository;
import com.springboot2.study.requests.AnimePostRequestBody;
import com.springboot2.study.util.AnimeCreator;
import com.springboot2.study.util.AnimePostRequestBodyCreator;
import com.springboot2.study.wrapper.PageableResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AnimeControllerIT {
	
	@Autowired
	@Qualifier(value = "testRestTemplateRoleUser")
	private TestRestTemplate testRestTemplateRoleUser;
	@Autowired
	@Qualifier(value = "testRestTemplateRoleAdmin")
	private TestRestTemplate testRestTemplateRoleAdmin;
	
//	@LocalServerPort
//	private int port;
	
	@Autowired
	private StudyUserRepository studyUserRepository;
	
	@Autowired
	private AnimeRepository animeRepository;
	
	private static final StudyUser USER = new StudyUser(
			"usuario teste",
			"userteste",
			"{bcrypt}$2a$10$CapzPeBeDjaTaWVpc/AV3OG9AqRdRNRtL0z/p69c5k2Gzu.gdFdEK",
			"ROLE_USER");
	
	private static final StudyUser ADMIN = new StudyUser(
			"admin teste",
			"adminteste",
			"{bcrypt}$2a$10$CapzPeBeDjaTaWVpc/AV3OG9AqRdRNRtL0z/p69c5k2Gzu.gdFdEK",
			"ROLE_USER,ROLE_ADMIN");
	
	
	@TestConfiguration
	@Lazy
	static class Config {
		
		@Bean(name = "testRestTemplateRoleUser")
		public TestRestTemplate testRestTemplateRoleUserCreator(@Value("${local.server.port}") int port) {
			RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
					.rootUri("http://localhost:"+port)
					.basicAuthentication("userteste", "senha");
			return new TestRestTemplate(restTemplateBuilder);
		}
		
		@Bean(name = "testRestTemplateRoleAdmin")
		public TestRestTemplate testRestTemplateRoleAdminCreator(@Value("${local.server.port}") int port) {
			RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
					.rootUri("http://localhost:"+port)
					.basicAuthentication("adminteste", "senha");
			return new TestRestTemplate(restTemplateBuilder);
		}

		
	}
	
	@Test
	@DisplayName("List returns list of anime inside page object when sucessful")
	void list_ReturnsListOfAnimesInsidePageObject_WhenSucessful() {
		
		Anime animeSaved = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
		studyUserRepository.save(USER);
		String expectedName = animeSaved.getName();
		
		PageableResponse<Anime> animePage = testRestTemplateRoleUser.exchange("/animes",
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
		studyUserRepository.save(USER);
		String expectedName = animeSaved.getName();
		
		List<Anime> animes = testRestTemplateRoleUser.exchange("/animes/all",
				HttpMethod.GET, null, new ParameterizedTypeReference<List<Anime>>() {
				}).getBody();
		
		Assertions.assertThat(animes).isNotNull().isNotEmpty().hasSize(1);
		Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
		
	}
	
	@Test
	@DisplayName("findById returns anime when sucessful")
	void findById_ReturnsAnime_WhenSucessful() {
		
		Anime animeSaved = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
		studyUserRepository.save(USER);
		Long expectedId = animeSaved.getId();
		
		Anime anime = testRestTemplateRoleUser.getForObject("/animes/{id}", Anime.class, expectedId);
		
		Assertions.assertThat(anime).isNotNull();
		Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(expectedId);
		
	}
	
	@Test
	@DisplayName("findByName returns a list of animes when sucessful")
	void findByName_ReturnsListOfAnime_WhenSucessful() {
		
		Anime animeSaved = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
		studyUserRepository.save(USER);
		String expectedName = animeSaved.getName();
		String url = String.format("/animes/find?name=%s", expectedName);
				
		List<Anime> animes = testRestTemplateRoleUser.exchange(url,
				HttpMethod.GET, null, new ParameterizedTypeReference<List<Anime>>() {
				}).getBody();
		
		Assertions.assertThat(animes).isNotNull().isNotEmpty().hasSize(1);
		Assertions.assertThat(animes.get(0).getName()).isNotNull().isEqualTo(AnimeCreator.createValidAnime().getName());
		
	}
	
	@Test
	@DisplayName("findByName returns an empty list of animes when anime is not found")
	void findByName_ReturnsEmptyListOfAnime_WhenAnimeIsNotFound() {
		studyUserRepository.save(USER);			
		List<Anime> animes = testRestTemplateRoleUser.exchange("/animes/find?name=dbz",
				HttpMethod.GET, null, new ParameterizedTypeReference<List<Anime>>() {
				}).getBody();
		
		Assertions.assertThat(animes).isNotNull().isEmpty();

	}
	
	@Test
	@DisplayName("save returns anime when sucessful")
	void save_ReturnsAnime_WhenSucessful() {
		
		AnimePostRequestBody animePostRequestBody = AnimePostRequestBodyCreator.createAnimePostRequestBody();
		studyUserRepository.save(ADMIN);
		ResponseEntity<Anime> animeResponseEntity = testRestTemplateRoleAdmin.postForEntity("/animes", animePostRequestBody, Anime.class);
		
		Assertions.assertThat(animeResponseEntity).isNotNull();
		Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		Assertions.assertThat(animeResponseEntity.getBody()).isNotNull();
		Assertions.assertThat(animeResponseEntity.getBody().getId()).isNotNull();
		
	}
	
	@Test
	@DisplayName("replace updates anime when sucessful")
	void replace_UpdatesAnime_WhenSucessful() {
		
		Anime animeSaved = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
		studyUserRepository.save(USER);
		animeSaved.setName("new name");
		
		ResponseEntity<Void> animeResponseEntity = testRestTemplateRoleUser.exchange("/animes", 
				HttpMethod.PUT, new HttpEntity<>(animeSaved), Void.class);
		
		Assertions.assertThat(animeResponseEntity).isNotNull();
		Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
		
	}
	
	@Test
	@DisplayName("delete removes anime when sucessful")
	void delete_RemovesAnime_WhenSucessful() {
		
		Anime animeSaved = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
		studyUserRepository.save(ADMIN);
		ResponseEntity<Void> animeResponseEntity = testRestTemplateRoleAdmin.exchange("/animes/admin/{id}", 
				HttpMethod.DELETE, null, Void.class, animeSaved.getId());
		
		Assertions.assertThat(animeResponseEntity).isNotNull();
		Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
		
	}
	
	@Test
	@DisplayName("delete returns 403 when user is not admin")
	void delete_Returns403_WhenUserIsNotAdmin() {
		
		Anime animeSaved = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
		studyUserRepository.save(USER);
		ResponseEntity<Void> animeResponseEntity = testRestTemplateRoleUser.exchange("/animes/admin/{id}", 
				HttpMethod.DELETE, null, Void.class, animeSaved.getId());
		
		Assertions.assertThat(animeResponseEntity).isNotNull();
		Assertions.assertThat(animeResponseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
		
	}
	
	
}
