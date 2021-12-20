package com.rest.RestaurantApp.dto;

import java.util.Set;
import java.util.stream.Collectors;
import com.rest.RestaurantApp.domain.SuggestedArticle;
import com.rest.RestaurantApp.domain.enums.ArticleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SuggestedArticleDTO {

	private Integer id;
	
	private Set<IngredientDTO> ingredients;
	
	private String name;
	
	private String description;
	
	private double suggestedMakingPrice;
	
	private double suggestedSellingPrice;
	
	private ArticleType type;
	
	public SuggestedArticleDTO(SuggestedArticle suggestedArticle) {
		super();
		this.id = suggestedArticle.getId();
		this.ingredients = suggestedArticle.getIngredients().stream().map(IngredientDTO::new).collect(Collectors.toSet());
		this.name = suggestedArticle.getName();
		this.description = suggestedArticle.getDescription();
		this.suggestedMakingPrice = suggestedArticle.getSuggestedMakingPrice();
		this.suggestedSellingPrice = suggestedArticle.getSuggestedSellingPrice();
		this.type = suggestedArticle.getType();		
	}
}
