package com.rest.RestaurantApp.services;

import java.util.List;
import com.rest.RestaurantApp.dto.SuggestedArticleDTO;

public interface ISuggestedArticleService {

	List<SuggestedArticleDTO> getAll();
	
	SuggestedArticleDTO getOne(int id);
	
	SuggestedArticleDTO delete(int id);
	
	SuggestedArticleDTO create(SuggestedArticleDTO article);
	
	SuggestedArticleDTO update(int id, SuggestedArticleDTO article);
}
