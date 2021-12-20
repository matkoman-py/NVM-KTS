package com.rest.RestaurantApp.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.rest.RestaurantApp.domain.Article;
import com.rest.RestaurantApp.domain.enums.ArticleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDTO {
	
	private Integer id;
	
	private List<IngredientDTO> ingredients;
	
	private String name;
	
	private double makingPrice;
	
	private double sellingPrice;
	
	private String description;
	
	private ArticleType type;
	
	public ArticleDTO(Article article) {
		super();
		this.id = article.getId();
		this.ingredients = article.getIngredients().stream().map(IngredientDTO::new).collect(Collectors.toList());
		this.name = article.getName();
		this.makingPrice = article.getActivePrice().getMakingPrice();
		this.sellingPrice = article.getActivePrice().getSellingPrice();
		this.description = article.getDescription();
		this.type = article.getType();
	}
}
