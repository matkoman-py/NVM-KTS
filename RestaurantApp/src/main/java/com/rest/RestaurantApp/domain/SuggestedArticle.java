package com.rest.RestaurantApp.domain;


import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import org.hibernate.annotations.Where;
import com.rest.RestaurantApp.domain.enums.ArticleType;

@Entity
@Where(clause = "deleted = false")
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

	public SuggestedArticle() {
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
	
	public double getSuggestedMakingPrice() {
		return suggestedMakingPrice;
	}

	public void setSuggestedMakingPrice(double suggestedMakingPrice) {
		this.suggestedMakingPrice = suggestedMakingPrice;
	}

	public double getSuggestedSellingPrice() {
		return suggestedSellingPrice;
	}

	public void setSuggestedSellingPrice(double suggestedSellingPrice) {
		this.suggestedSellingPrice = suggestedSellingPrice;
	}

	public ArticleType getType() {
		return type;
	}

	public void setType(ArticleType type) {
		this.type = type;
	}
	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SuggestedArticle suggestedArticle = (SuggestedArticle) o;
        if (suggestedArticle.getId() == null || this.getId() == null) {
            if(suggestedArticle.getName().equals(this.getName())){
                return true;
            }
            return false;
        }
        return Objects.equals(this.getId(), suggestedArticle.getId());
    }
}
