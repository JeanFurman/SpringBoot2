package com.springboot2.study.util;

import com.springboot2.study.domain.Anime;

public class AnimeCreator {

	public static Anime createAnimeToBeSaved() {
		return new Anime("Hajime no Ippo");
	}
	
	public static Anime createValidAnime() {
		return new Anime(1L, "Hajime no Ippo");
	}
	
	public static Anime createValidUpdatedAnime() {
		return new Anime(1L, "Hajime no Ippo 2");
	}
	
}
