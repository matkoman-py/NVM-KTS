package com.rest.RestaurantApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rest.RestaurantApp.domain.Ingredient;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Integer>{

}
