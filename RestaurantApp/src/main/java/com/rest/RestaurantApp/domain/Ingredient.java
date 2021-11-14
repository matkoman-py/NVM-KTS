package com.rest.RestaurantApp.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import org.hibernate.annotations.Where;

@Entity
@Where(clause = "deleted = false")
public class Ingredient extends BaseEntity {
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private boolean isAllergen;
	
	@ManyToMany(mappedBy = "ingredients")
	private Set<Article> articles;

	public Ingredient(String name, boolean isAllergen) {
		super();
		this.name = name;
		this.isAllergen = isAllergen;
		this.articles = new HashSet<Article>();
	}

	public Ingredient() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Article> getArticles() {
		return articles;
	}

	public void setArticles(Set<Article> articles) {
		this.articles = articles;
	}

	public boolean isAllergen() {
		return isAllergen;
	}

	public void setAllergen(boolean isAllergen) {
		this.isAllergen = isAllergen;
	}	
	
	
}
