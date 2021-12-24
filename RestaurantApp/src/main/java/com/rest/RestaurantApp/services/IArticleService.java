package com.rest.RestaurantApp.services;

import java.util.List;

import com.rest.RestaurantApp.dto.ArticleCreationDTO;
import com.rest.RestaurantApp.dto.ArticleDTO;

public interface IArticleService {
	
	List<ArticleDTO> getAll();
	
	List<ArticleDTO> search(String type);
	
	ArticleDTO getOne(int id);
	
	ArticleDTO delete(int id);
	
	ArticleDTO create(ArticleCreationDTO article);
	
	ArticleDTO update(int id, ArticleDTO article);
}
