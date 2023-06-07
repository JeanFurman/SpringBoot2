package com.springboot2.study.repository;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.springboot2.study.domain.Anime;

import jakarta.validation.ConstraintViolationException;

@DataJpaTest
@DisplayName("Tests for Anime Repository")
class AnimeRepositoryTest {

	@Autowired
	private AnimeRepository animeRepository;
	
	@Test
	@DisplayName("Save persists anime when Sucessful")
	void save_PersistAnime_WhenSucessful() {
		Anime animeToBeSaved = createAnime();
		Anime animeSaved = this.animeRepository.save(animeToBeSaved);
		
		Assertions.assertThat(animeSaved).isNotNull();
		Assertions.assertThat(animeSaved.getId()).isNotNull();
		Assertions.assertThat(animeSaved.getName()).isEqualTo(animeToBeSaved.getName());
	}
	
	@Test
	@DisplayName("Save updates anime when Sucessful")
	void save_UpdatedAnime_WhenSucessful() {
		Anime animeToBeSaved = createAnime();
		Anime animeSaved = this.animeRepository.save(animeToBeSaved);
		animeSaved.setName("Overlord");
		Anime animeUpdated = this.animeRepository.save(animeSaved);
		
		Assertions.assertThat(animeUpdated).isNotNull();
		Assertions.assertThat(animeUpdated.getId()).isNotNull();
		Assertions.assertThat(animeUpdated.getName()).isEqualTo(animeSaved.getName());
	}
	
	@Test
	@DisplayName("Delete removes anime when Sucessful")
	void delete_RemovesAnime_WhenSucessful() {
		Anime animeToBeSaved = createAnime();
		Anime animeSaved = this.animeRepository.save(animeToBeSaved);
		this.animeRepository.delete(animeSaved);
		Optional<Anime> animeOptional = this.animeRepository.findById(animeSaved.getId());
		
		Assertions.assertThat(animeOptional).isEmpty();
	}
	
	@Test
	@DisplayName("Find by name returns list of anime when Sucessful")
	void findByName_ReturnsListOfAnime_WhenSucessful() {
		Anime animeToBeSaved = createAnime();
		Anime animeSaved = this.animeRepository.save(animeToBeSaved);
		String name = animeSaved.getName();
		List<Anime> animes = this.animeRepository.findByName(name);
		
		Assertions.assertThat(animes).isNotEmpty().contains(animeSaved);
	}
	
	@Test
	@DisplayName("Find by name returns empty list when anime is found")
	void findByName_ReturnsListEmpty_WhenAnimeIsNotFound() {
		List<Anime> animes = this.animeRepository.findByName("xaxa");
		
		Assertions.assertThat(animes).isEmpty();
	}
	
	@Test
	@DisplayName("Save throw ConstraintValidationException when name is empty")
	void save_ThrowsConstraintValidationException_WhenNameIsEmpty() {
		Anime anime = new Anime();
		
		Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
		.isThrownBy(() -> this.animeRepository.save(anime)).withMessageContaining("The anime name can't be empty");
	}
	
	private Anime createAnime() {
		return new Anime("Hajime no Ippo");
	}
}
