package com.rest.RestaurantApp.dto;

import java.util.List;

public class MenuDTO {
	private List<ArticleDTO> articles;

	public MenuDTO(List<ArticleDTO> articles) {
		super();
		this.articles = articles;
	}

	public MenuDTO() {
		super();
	}

	public List<ArticleDTO> getArticles() {
		return articles;
	}

	public void setArticles(List<ArticleDTO> articles) {
		this.articles = articles;
	}
	
}
