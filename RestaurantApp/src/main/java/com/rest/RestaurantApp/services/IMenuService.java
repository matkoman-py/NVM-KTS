package com.rest.RestaurantApp.services;

import com.rest.RestaurantApp.dto.MenuDTO;

public interface IMenuService {
	
	MenuDTO getMenu();
	
	MenuDTO deleteArticle(int id);
	
	MenuDTO addArticle(int id);
	
}
