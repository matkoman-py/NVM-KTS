package com.rest.RestaurantApp.domain;


import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;
import com.rest.RestaurantApp.domain.enums.ArticleType;

@Entity
@Where(clause = "deleted = false")
@Getter
@Setter
@NoArgsConstructor
public class SuggestedArticle extends BaseEntity{
	
	@ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
        name = "suggested_article_ingredient", 
        joinColumns = { @JoinColumn(name = "suggested_article_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "ingredient_id") }
    )
    private Set<Ingredient> ingredients;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String description;
	
	@Column(nullable = false)
	private double suggestedMakingPrice;
	
	@Column(nullable = false)
	private double suggestedSellingPrice;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ArticleType type;

	public SuggestedArticle(String name, String description, double suggestedMakingPrice, double suggestedSellingPrice, ArticleType type) {
		super();
		this.ingredients = new HashSet<>();
		this.name = name;
		this.description = description;
		this.suggestedMakingPrice = suggestedMakingPrice;
		this.suggestedSellingPrice = suggestedSellingPrice;	
		this.type = type;
	}

}
