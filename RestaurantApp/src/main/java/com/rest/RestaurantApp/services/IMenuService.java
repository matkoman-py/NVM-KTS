package com.rest.RestaurantApp.services;

import com.rest.RestaurantApp.dto.MenuDTO;

public interface IMenuService {
	
	MenuDTO getMenu(int menuId);
	
	MenuDTO deleteArticle(int menuId, int id);
	
	MenuDTO addArticle(int menuId, int id);
	
}
