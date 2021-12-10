package com.rest.RestaurantApp.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

@Entity
@Where(clause = "deleted = false")
@Getter
@Setter
@NoArgsConstructor
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

}
