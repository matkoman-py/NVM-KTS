package com.rest.RestaurantApp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.RestaurantApp.domain.Article;
import com.rest.RestaurantApp.domain.Ingredient;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Integer>{
	
	List<Ingredient> findByNameContainingIgnoreCase(String name);
}
