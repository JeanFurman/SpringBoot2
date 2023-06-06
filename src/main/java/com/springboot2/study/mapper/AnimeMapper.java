package com.springboot2.study.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.springboot2.study.domain.Anime;
import com.springboot2.study.requests.AnimePostRequestBody;
import com.springboot2.study.requests.AnimePutRequestBody;

@Mapper(componentModel = "spring")
public abstract class AnimeMapper {
	
	public static final AnimeMapper INSTANCE = Mappers.getMapper(AnimeMapper.class);
	
	public abstract Anime toAnime(AnimePostRequestBody animePostRequestBody);
	
	public abstract Anime toAnime(AnimePutRequestBody animePutRequestBody);
}
