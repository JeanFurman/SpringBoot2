package com.springboot2.study.controller;

import java.util.Collections;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.springboot2.study.domain.Anime;
import com.springboot2.study.requests.AnimePostRequestBody;
import com.springboot2.study.requests.AnimePutRequestBody;
import com.springboot2.study.service.AnimeService;
import com.springboot2.study.util.AnimeCreator;
import com.springboot2.study.util.AnimePostRequestBodyCreator;
import com.springboot2.study.util.AnimePutRequestBodyCreator;

@ExtendWith(SpringExtension.class)
public class AnimeControllerTest {

	@InjectMocks
	private AnimeController animeController;
	
	@Mock
	private AnimeService animeServiceMock;

	@BeforeEach
	void setUp() {
		PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidAnime()));
		BDDMockito.when(animeServiceMock.listAll(ArgumentMatchers.any())).thenReturn(animePage);
		
		BDDMockito.when(animeServiceMock.listAllNonPageable())
		.thenReturn(List.of(AnimeCreator.createValidAnime()));
		
		BDDMockito.when(animeServiceMock.findByIdOrThrowBadRequestException(ArgumentMatchers.anyLong()))
		.thenReturn(AnimeCreator.createValidAnime());
		
		BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString()))
		.thenReturn(List.of(AnimeCreator.createValidAnime()));
		
		BDDMockito.when(animeServiceMock.save(ArgumentMatchers.any(AnimePostRequestBody.class)))
		.thenReturn(AnimeCreator.createValidAnime());
		
		BDDMockito.doNothing().when(animeServiceMock).replace(ArgumentMatchers.any(AnimePutRequestBody.class));
		
		BDDMockito.doNothing().when(animeServiceMock).delete(ArgumentMatchers.anyLong());

	}
	
	@Test
	@DisplayName("List returns list of anime inside page object when sucessful")
	void list_ReturnsListOfAnimesInsidePageObject_WhenSucessful() {
		String expectedName = AnimeCreator.createValidAnime().getName();
		
		Page<Anime> animePage = animeController.list(null).getBody();
		
		Assertions.assertThat(animePage).isNotNull();
		Assertions.assertThat(animePage.toList()).isNotEmpty().hasSize(1);
		Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
		
	}
	
	@Test
	@DisplayName("List returns list of animes when sucessful")
	void listAll_ReturnsListOfAnimes_WhenSucessful() {
		String expectedName = AnimeCreator.createValidAnime().getName();
		
		List<Anime> animes = animeController.listAll().getBody();
		
		Assertions.assertThat(animes).isNotNull().isNotEmpty().hasSize(1);
		Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
		
	}
	
	@Test
	@DisplayName("findById returns anime when sucessful")
	void findById_ReturnsAnime_WhenSucessful() {
		Long expectedId = AnimeCreator.createValidAnime().getId();
		
		Anime anime = animeController.findById(1).getBody();
		
		Assertions.assertThat(anime).isNotNull();
		Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(expectedId);
		
	}
	
	@Test
	@DisplayName("findByName returns a list of animes when sucessful")
	void findByName_ReturnsListOfAnime_WhenSucessful() {
				
		List<Anime> animes = animeController.findByName("anime").getBody();
		
		Assertions.assertThat(animes).isNotNull().isNotEmpty().hasSize(1);
		Assertions.assertThat(animes.get(0).getName()).isNotNull().isEqualTo(AnimeCreator.createValidAnime().getName());
		
	}
	
	@Test
	@DisplayName("findByName returns an empty list of animes when anime is not found")
	void findByName_ReturnsEmptyListOfAnime_WhenAnimeIsNotFound() {
		
		BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString()))
		.thenReturn(Collections.emptyList());
				
		List<Anime> animes = animeController.findByName("anime").getBody();
		
		Assertions.assertThat(animes).isNotNull().isEmpty();
		
	}
	
	@Test
	@DisplayName("save returns anime when sucessful")
	void save_ReturnsAnime_WhenSucessful() {
		
		Anime anime = animeController.save(AnimePostRequestBodyCreator.createAnimePostRequestBody()).getBody();
		
		Assertions.assertThat(anime).isNotNull().isEqualTo(AnimeCreator.createValidAnime());
		
	}
	
	@Test
	@DisplayName("replace updates anime when sucessful")
	void replace_UpdatesAnime_WhenSucessful() {
		
		Assertions.assertThatCode(() -> animeController.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody())).doesNotThrowAnyException();
		
		ResponseEntity<Void> entity = animeController.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody());
		
		Assertions.assertThat(entity).isNotNull();
		Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
		
	}
	
	@Test
	@DisplayName("delete removes anime when sucessful")
	void delete_RemovesAnime_WhenSucessful() {
		
		Assertions.assertThatCode(() -> animeController.delete(1)).doesNotThrowAnyException();
		
		ResponseEntity<Void> entity = animeController.delete(1);
		
		Assertions.assertThat(entity).isNotNull();
		Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
		
	}
}
