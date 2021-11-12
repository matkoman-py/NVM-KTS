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
	private double price;
	
	@ManyToMany(mappedBy = "ingredients")
	private Set<Article> articles;

	public Ingredient(String name, double price) {
		super();
		this.name = name;
		this.price = price;
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Set<Article> getArticles() {
		return articles;
	}

	public void setArticles(Set<Article> articles) {
		this.articles = articles;
	}	
	
	
}
