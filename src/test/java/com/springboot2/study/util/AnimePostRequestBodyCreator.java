package com.springboot2.study.util;

import com.springboot2.study.requests.AnimePostRequestBody;

public class AnimePostRequestBodyCreator {

	public static AnimePostRequestBody createAnimePostRequestBody() {
		return new AnimePostRequestBody(AnimeCreator.createAnimeToBeSaved().getName());
	}
	
}
