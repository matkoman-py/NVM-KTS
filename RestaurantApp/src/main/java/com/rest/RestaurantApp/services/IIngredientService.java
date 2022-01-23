package com.rest.RestaurantApp.services;

import com.rest.RestaurantApp.dto.ArticleCreationDTO;
import com.rest.RestaurantApp.dto.ArticleDTO;
import com.rest.RestaurantApp.dto.IngredientDTO;

import java.util.List;

public interface IIngredientService {
    List<IngredientDTO> getAll();
    
    List<IngredientDTO> search(String name, String type);

    IngredientDTO getOne(int id);

    IngredientDTO delete(int id);

    IngredientDTO create(IngredientDTO article);

    IngredientDTO update(int id, IngredientDTO article);
}
