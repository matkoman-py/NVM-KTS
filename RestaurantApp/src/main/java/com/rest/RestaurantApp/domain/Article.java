package com.rest.RestaurantApp.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Where;


import com.rest.RestaurantApp.domain.enums.ArticleType;
import com.rest.RestaurantApp.domain.enums.PriceStatus;

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
		
		@OneToMany(mappedBy = "article")
		private List<PriceInfo> prices;
		
		@Enumerated(EnumType.STRING)
		@Column(nullable = false)
		private ArticleType type;
		
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "menu_id", nullable = true)
		private Menu menu;
		
		@OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
		private Set<OrderedArticle> orderedArticles;

		public Article(String name, String description, ArticleType type) {
			super();
			this.ingredients = new HashSet<>();
			this.name = name;
			this.description = description;
			this.prices = new ArrayList<PriceInfo>();
			this.type = type;
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

		public List<PriceInfo> getPrices() {
			return prices;
		}

		public void setPrices(List<PriceInfo>  prices) {
			this.prices = prices;
		}
		
		public PriceInfo getActivePrice() {
			return prices.stream().filter(price -> price.getStatus().equals(PriceStatus.ACTIVE)).findAny().orElse(null);
			
		}
		
		public void setNewPrice(PriceInfo priceInfo) {
			System.out.println("UDJEm");
			if(prices.size() > 0) {
				PriceInfo oldPrice = prices.stream().filter(price -> price.getStatus().equals(PriceStatus.ACTIVE)).findAny().orElse(null);
				oldPrice.setToDate(new Date());
				oldPrice.setStatus(PriceStatus.EXPIRED);
			}
			prices.add(priceInfo);
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
		
		
		@Override
	    public boolean equals(Object o) {
	        if (this == o) {
	            return true;
	        }
	        if (o == null || getClass() != o.getClass()) {
	            return false;
	        }
	        Article article = (Article) o;
	        if (article.getId() == null || this.getId() == null) {
	            if(article.getName().equals(this.getName())){
	                return true;
	            }
	            return false;
	        }
	        return Objects.equals(this.getId(), article.getId());
	    }
}
