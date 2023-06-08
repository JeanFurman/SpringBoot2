package com.springboot2.study.util;

import com.springboot2.study.requests.AnimePutRequestBody;

public class AnimePutRequestBodyCreator {

	public static AnimePutRequestBody createAnimePutRequestBody() {
		return new AnimePutRequestBody(AnimeCreator.createValidAnime().getId(), AnimeCreator.createValidAnime().getName());
	}
	
}
