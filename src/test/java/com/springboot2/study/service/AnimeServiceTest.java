package com.springboot2.study.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.springboot2.study.domain.Anime;
import com.springboot2.study.exception.BadRequestException;
import com.springboot2.study.repository.AnimeRepository;
import com.springboot2.study.util.AnimeCreator;
import com.springboot2.study.util.AnimePostRequestBodyCreator;
import com.springboot2.study.util.AnimePutRequestBodyCreator;

@ExtendWith(SpringExtension.class)
public class AnimeServiceTest {
	@InjectMocks
	private AnimeService animeService;
	
	@Mock
	private AnimeRepository animeRepositoryMock;

	@BeforeEach
	void setUp() {
		PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidAnime()));
		BDDMockito.when(animeRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class))).thenReturn(animePage);
		
		BDDMockito.when(animeRepositoryMock.findAll())
		.thenReturn(List.of(AnimeCreator.createValidAnime()));
		
		BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
		.thenReturn(Optional.of(AnimeCreator.createValidAnime()));
		
		BDDMockito.when(animeRepositoryMock.findByName(ArgumentMatchers.anyString()))
		.thenReturn(List.of(AnimeCreator.createValidAnime()));
		
		BDDMockito.when(animeRepositoryMock.save(ArgumentMatchers.any(Anime.class)))
		.thenReturn(AnimeCreator.createValidAnime());
			
		BDDMockito.doNothing().when(animeRepositoryMock).delete(ArgumentMatchers.any(Anime.class));

	}
	
	@Test
	@DisplayName("listAll returns list of anime inside page object when sucessful")
	void listAll_ReturnsListOfAnimesInsidePageObject_WhenSucessful() {
		String expectedName = AnimeCreator.createValidAnime().getName();
		
		Page<Anime> animePage = animeService.listAll(PageRequest.of(1,1));
		
		Assertions.assertThat(animePage).isNotNull();
		Assertions.assertThat(animePage.toList()).isNotEmpty().hasSize(1);
		Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
		
	}
	
	@Test
	@DisplayName("listAllNonPageable returns list of animes when sucessful")
	void listAllNonPageable_ReturnsListOfAnimes_WhenSucessful() {
		String expectedName = AnimeCreator.createValidAnime().getName();
		
		List<Anime> animes = animeService.listAllNonPageable();
		
		Assertions.assertThat(animes).isNotNull().isNotEmpty().hasSize(1);
		Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
		
	}
	
	@Test
	@DisplayName("findByIdOrThrowBadRequestException returns anime when sucessful")
	void findByIdOrThrowBadRequestException_ReturnsAnime_WhenSucessful() {
		Long expectedId = AnimeCreator.createValidAnime().getId();
		
		Anime anime = animeService.findByIdOrThrowBadRequestException(1);
		
		Assertions.assertThat(anime).isNotNull();
		Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(expectedId);
		
	}
	
	@Test
	@DisplayName("findByIdOrThrowBadRequestException throws BadRequestException when Anime is not found")
	void findByIdOrThrowBadRequestException_ThrowsBadRequestException_WhenAnimeIsNotFound() {
		
		BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
		.thenReturn(Optional.empty());
		
		Assertions.assertThatExceptionOfType(BadRequestException.class)
		.isThrownBy(() -> animeService.findByIdOrThrowBadRequestException(1)).withMessageContaining("Anime not found");
		
	}
	
	@Test
	@DisplayName("findByName returns a list of animes when sucessful")
	void findByName_ReturnsListOfAnime_WhenSucessful() {
				
		List<Anime> animes = animeService.findByName("anime");
		
		Assertions.assertThat(animes).isNotNull().isNotEmpty().hasSize(1);
		Assertions.assertThat(animes.get(0).getName()).isNotNull().isEqualTo(AnimeCreator.createValidAnime().getName());
		
	}
	
	@Test
	@DisplayName("findByName returns an empty list of animes when anime is not found")
	void findByName_ReturnsEmptyListOfAnime_WhenAnimeIsNotFound() {
		
		BDDMockito.when(animeRepositoryMock.findByName(ArgumentMatchers.anyString()))
		.thenReturn(Collections.emptyList());
				
		List<Anime> animes = animeService.findByName("anime");
		
		Assertions.assertThat(animes).isNotNull().isEmpty();
		
	}
	
	@Test
	@DisplayName("save returns anime when sucessful")
	void save_ReturnsAnime_WhenSucessful() {
		
		Anime anime = animeService.save(AnimePostRequestBodyCreator.createAnimePostRequestBody());
		
		Assertions.assertThat(anime).isNotNull().isEqualTo(AnimeCreator.createValidAnime());
		
	}
	
	@Test
	@DisplayName("replace updates anime when sucessful")
	void replace_UpdatesAnime_WhenSucessful() {
		
		Assertions.assertThatCode(() -> animeService.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody())).doesNotThrowAnyException();
		
	}
	
	@Test
	@DisplayName("delete removes anime when sucessful")
	void delete_RemovesAnime_WhenSucessful() {
		
		Assertions.assertThatCode(() -> animeService.delete(1)).doesNotThrowAnyException();
		
	}
}
