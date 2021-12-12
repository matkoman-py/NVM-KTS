package com.rest.RestaurantApp.dto;

import java.util.Set;
import java.util.stream.Collectors;
import com.rest.RestaurantApp.domain.SuggestedArticle;
import com.rest.RestaurantApp.domain.enums.ArticleType;

public class SuggestedArticleDTO {

	private Integer id;
	
	private Set<IngredientDTO> ingredients;
	
	private String name;
	
	private String description;
	
	private double suggestedMakingPrice;
	
	public SuggestedArticleDTO() {
		super();
	}

	private double suggestedSellingPrice;
	
	private ArticleType type;

	public SuggestedArticleDTO(Integer id, Set<IngredientDTO> ingredients, String name, String description, double suggestedMakingPrice,
			double suggestedSellingPrice, ArticleType type) {
		super();
		this.id = id;
		this.ingredients = ingredients;
		this.name = name;
		this.description = description;
		this.suggestedMakingPrice = suggestedMakingPrice;
		this.suggestedSellingPrice = suggestedSellingPrice;
		this.type = type;
	}
	
	public SuggestedArticleDTO(SuggestedArticle suggestedArticle) {
		super();
		this.id = suggestedArticle.getId();
		this.ingredients = suggestedArticle.getIngredients().stream().map(ingredient ->new IngredientDTO(ingredient)).collect(Collectors.toSet());
		this.name = suggestedArticle.getName();
		this.description = suggestedArticle.getDescription();
		this.suggestedMakingPrice = suggestedArticle.getSuggestedMakingPrice();
		this.suggestedSellingPrice = suggestedArticle.getSuggestedSellingPrice();
		this.type = suggestedArticle.getType();		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Set<IngredientDTO> getIngredients() {
		return ingredients;
	}

	public void setIngredients(Set<IngredientDTO> ingredients) {
		this.ingredients = ingredients;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getSuggestedMakingPrice() {
		return suggestedMakingPrice;
	}

	public void setSuggestedMakingPrice(double suggestedMakingPrice) {
		this.suggestedMakingPrice = suggestedMakingPrice;
	}

	public double getSuggestedSellingPrice() {
		return suggestedSellingPrice;
	}

	public void setSuggestedSellingPrice(double suggestedSellingPrice) {
		this.suggestedSellingPrice = suggestedSellingPrice;
	}

	public ArticleType getType() {
		return type;
	}

	public void setType(ArticleType type) {
		this.type = type;
	}
	
	
}
