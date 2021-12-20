package com.rest.RestaurantApp.dto;

import java.util.List;

import com.rest.RestaurantApp.domain.enums.ArticleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleCreationDTO {
	
	private List<IngredientDTO> ingredients;
	
	private String name;
	
	private double makingPrice;
	
	private double sellingPrice;
	
	private String description;
	
	private ArticleType type;

}
