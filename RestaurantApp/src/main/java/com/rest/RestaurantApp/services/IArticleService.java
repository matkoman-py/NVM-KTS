package com.rest.RestaurantApp.services;

import java.util.List;

import com.rest.RestaurantApp.dto.ArticleDTO;

public interface IArticleService {
	
	List<ArticleDTO> getAll();
	
	ArticleDTO getOne(int id);
	
	ArticleDTO delete(int id);
	
	ArticleDTO create(ArticleDTO article);
	
	ArticleDTO update(int id, ArticleDTO article);
}
