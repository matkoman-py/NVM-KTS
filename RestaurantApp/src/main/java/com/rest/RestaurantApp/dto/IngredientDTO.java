package com.rest.RestaurantApp.dto;

import com.rest.RestaurantApp.domain.Ingredient;

public class IngredientDTO {
	private Integer id;
	private String name;
	private boolean isAllergen;

	public IngredientDTO(Integer id,String name, boolean isAllergen) {
		super();
		this.id = id;
		this.name = name;
		this.isAllergen = isAllergen;
	}
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
	
	public IngredientDTO() {
		super();
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isAllergen() {
		return isAllergen;
	}
	public void setAllergen(boolean isAllergen) {
		this.isAllergen = isAllergen;
	}
	
	
}
