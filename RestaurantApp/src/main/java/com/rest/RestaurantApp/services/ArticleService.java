package com.rest.RestaurantApp.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rest.RestaurantApp.domain.Article;
import com.rest.RestaurantApp.domain.Ingredient;
import com.rest.RestaurantApp.domain.PriceInfo;
import com.rest.RestaurantApp.dto.ArticleDTO;
import com.rest.RestaurantApp.repositories.ArticleRepository;
import com.rest.RestaurantApp.repositories.IngredientRepository;
import com.rest.RestaurantApp.repositories.PriceInfoRepository;

@Service
@Transactional
public class ArticleService implements IArticleService {
	
	private ArticleRepository articleRepository;
	
	private IngredientRepository ingredientRepository;
	
	private PriceInfoRepository priceInfoRepository;
	
	
	@Autowired
	public ArticleService(ArticleRepository articleRepository, IngredientRepository ingredientRepository, PriceInfoRepository priceInfoRepository) {
		this.articleRepository = articleRepository;
		this.ingredientRepository = ingredientRepository;
		this.priceInfoRepository = priceInfoRepository;
	}
	
	@Override
	public List<ArticleDTO> getAll() {
		return articleRepository.findAll().stream().map(article -> new ArticleDTO(article)).collect(Collectors.toList());
	}
	
	@Override
	public ArticleDTO getOne(int id) {
		Optional<Article> article =  articleRepository.findById(id);
		if(article.isEmpty()) {
			return null;
		}
		return new ArticleDTO(article.get());
	}
	
	@Override
	public ArticleDTO delete(int id) {
		Optional<Article> articleData =  articleRepository.findById(id);
		if(articleData.isEmpty()) {
			return null;
		}
		Article article = articleData.get();
		article.setDeleted(true);
		articleRepository.save(article);
		return new ArticleDTO(article);
	}
	
	@Override
	public ArticleDTO create(ArticleDTO article) {
		Article newArticle = new Article(article.getName(), article.getDescription(),article.getType());
		PriceInfo priceInfo = new PriceInfo(new Date(), article.getMakingPrice(), article.getSellingPrice(), newArticle);
		priceInfoRepository.save(priceInfo);
		newArticle.setNewPrice(priceInfo);
		Set<Ingredient> ingredients = article.getIngredients().stream()
				.map(ingredientDTO -> ingredientRepository.findById(ingredientDTO.getId()).orElse(null)).collect(Collectors.toSet());
		newArticle.setIngredients(ingredients);
		return new ArticleDTO(articleRepository.save(newArticle));
	}
	
	@Override
	public ArticleDTO update(int id, ArticleDTO article) {
		Optional<Article> oldArticleData = articleRepository.findById(id);
		if(oldArticleData.isEmpty()) {
			return null;
		}
		Article oldArticle = oldArticleData.get();
		oldArticle.setName(article.getName());
		oldArticle.setDescription(article.getDescription());
		if(oldArticle.getActivePrice().getMakingPrice() != article.getMakingPrice() || oldArticle.getActivePrice().getSellingPrice() != article.getSellingPrice()) {
			PriceInfo priceInfo = new PriceInfo(new Date(), article.getMakingPrice(), article.getSellingPrice(), oldArticle);
			priceInfoRepository.save(priceInfo);
			oldArticle.setNewPrice(priceInfo);
		}
		oldArticle.setType(article.getType());
		Set<Ingredient> ingredients = article.getIngredients().stream()
				.map(ingredientDTO -> ingredientRepository.findById(ingredientDTO.getId()).orElse(null)).collect(Collectors.toSet());
		oldArticle.setIngredients(ingredients);
		Article updatedArticle = articleRepository.save(oldArticle);
		return new ArticleDTO(updatedArticle);
	}
}
