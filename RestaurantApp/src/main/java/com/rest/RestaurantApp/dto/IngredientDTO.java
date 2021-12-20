package com.rest.RestaurantApp.dto;

import com.rest.RestaurantApp.domain.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IngredientDTO {
	private Integer id;
	private String name;
	private boolean isAllergen;

	public IngredientDTO(String name, boolean isAllergen) {
		super();
		this.name = name;
		this.isAllergen = isAllergen;
	}
	public IngredientDTO(Ingredient ingredient) {
		super();
		this.id = ingredient.getId();
		this.name = ingredient.getName();
		this.isAllergen = ingredient.isAllergen();
	}

}
