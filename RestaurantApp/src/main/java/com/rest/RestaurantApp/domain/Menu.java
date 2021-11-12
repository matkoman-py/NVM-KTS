package com.rest.RestaurantApp.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import org.hibernate.annotations.Where;

@Entity
@Where(clause = "deleted = false")
public class Menu extends BaseEntity{

	@OneToMany(mappedBy = "menu")
	private Set<Article> articles;

	public Menu() {
		super();
		this.articles = new HashSet<>();
	}

	public Set<Article> getArticles() {
		return articles;
	}

	public void setArticles(Set<Article> articles) {
		this.articles = articles;
	}
	
	
	
}
