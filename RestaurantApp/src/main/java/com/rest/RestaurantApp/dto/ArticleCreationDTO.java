package com.rest.RestaurantApp.dto;

import java.util.ArrayList;
import java.util.List;

import com.rest.RestaurantApp.domain.enums.ArticleType;

public class ArticleCreationDTO {
	
	private List<IngredientDTO> ingredients;
	
	private String name;
	
	private double makingPrice;
	
	private double sellingPrice;
	
	private String description;
	
	private String image;
	
	private ArticleType type;

	public ArticleCreationDTO(List<IngredientDTO> ingredients, String name, double makingPrice, double sellingPrice,
			String description, ArticleType type, String image) {
		super();
		this.ingredients = ingredients;
		this.name = name;
		this.makingPrice = makingPrice;
		this.sellingPrice = sellingPrice;
		this.description = description;
		this.type = type;
		this.image = image;
	}

	public ArticleCreationDTO(List<IngredientDTO> ingredients, String name, double makingPrice, double sellingPrice,
							  String description, ArticleType type) {
		this.ingredients = ingredients;
		this.name = name;
		this.makingPrice = makingPrice;
		this.sellingPrice = sellingPrice;
		this.description = description;
		this.type = type;
	}

	public ArticleCreationDTO() {
		super();
	}

	public List<IngredientDTO> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<IngredientDTO> ingredients) {
		this.ingredients = ingredients;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getMakingPrice() {
		return makingPrice;
	}

	public void setMakingPrice(double makingPrice) {
		this.makingPrice = makingPrice;
	}

	public double getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ArticleType getType() {
		return type;
	}

	public void setType(ArticleType type) {
		this.type = type;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	
}
