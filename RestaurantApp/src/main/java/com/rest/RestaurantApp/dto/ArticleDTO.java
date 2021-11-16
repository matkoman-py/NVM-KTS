package com.rest.RestaurantApp.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.rest.RestaurantApp.domain.Article;
import com.rest.RestaurantApp.domain.enums.ArticleType;

public class ArticleDTO {
	
	private Integer id;
	
	private List<IngredientDTO> ingredients;
	
	private String name;
	
	private double makingPrice;
	
	private double sellingPrice;
	
	private String description;
	
	private ArticleType type;

	public ArticleDTO(Integer id, List<IngredientDTO> ingredients, String name, double makingPrice, double sellingPrice, String description,
			ArticleType type) {
		super();
		this.id = id;
		this.ingredients = ingredients;
		this.name = name;
		this.makingPrice = makingPrice;
		this.sellingPrice = sellingPrice;
		this.description = description;
		this.type = type;
	}
	
	public ArticleDTO(Article article) {
		super();
		this.id = article.getId();
		this.ingredients = article.getIngredients().stream().map(ingredient -> new IngredientDTO(ingredient)).collect(Collectors.toList());
		this.name = article.getName();
		this.makingPrice = article.getActivePrice().getMakingPrice();
		this.sellingPrice = article.getActivePrice().getSellingPrice();
//		this.makingPrice = 100;
//		this.sellingPrice = 200;
		this.description = article.getDescription();
		this.type = article.getType();
	}
	
	public ArticleDTO() {
		super();
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
	
	
}
