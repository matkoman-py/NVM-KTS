package com.rest.RestaurantApp.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Where;

import com.rest.RestaurantApp.domain.enums.ArticleType;

import javax.persistence.JoinColumn;

@Entity
@Where(clause = "deleted = false")
public class Article extends BaseEntity{
	 
		@ManyToMany(cascade = { CascadeType.ALL })
	    @JoinTable(
	        name = "article_ingredient", 
	        joinColumns = { @JoinColumn(name = "article_id") }, 
	        inverseJoinColumns = { @JoinColumn(name = "ingredient_id") }
	    )
	    private Set<Ingredient> ingredients;
		
		@Column(nullable = false)
		private String name;
		
		@Column(nullable = false)
		private String description;
		
		@Column(nullable = false)
		private double price;
		
		@Column(nullable = false)
		private ArticleType type;
		
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "menu_id", nullable = false)
		private Menu menu;
		
		@OneToMany(mappedBy = "article")
		private Set<OrderedArticle> orderedArticles;

		public Article(String name, String description, double price, ArticleType type) {
			super();
			this.ingredients = new HashSet<>();
			this.name = name;
			this.description = description;
			this.price = price;
			this.type = type;
			this.menu = new Menu();
			this.orderedArticles = new HashSet<>();
		}

		public Article() {
			super();
		}

		public Set<Ingredient> getIngredients() {
			return ingredients;
		}

		public void setIngredients(Set<Ingredient> ingredients) {
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

		public double getPrice() {
			return price;
		}

		public void setPrice(double price) {
			this.price = price;
		}

		public ArticleType getType() {
			return type;
		}

		public void setType(ArticleType type) {
			this.type = type;
		}

		public Menu getMenu() {
			return menu;
		}

		public void setMenu(Menu menu) {
			this.menu = menu;
		}

		public Set<OrderedArticle> getOrderedArticles() {
			return orderedArticles;
		}

		public void setOrderedArticles(Set<OrderedArticle> orderedArticles) {
			this.orderedArticles = orderedArticles;
		}
		
		
		
}
