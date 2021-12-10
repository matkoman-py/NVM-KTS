package com.rest.RestaurantApp.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

@Entity
@Where(clause = "deleted = false")
@Getter
@Setter
public class Menu extends BaseEntity{

	@OneToMany(mappedBy = "menu")
	private Set<Article> articles;

	public Menu() {
		super();
		this.articles = new HashSet<>();
	}
}
