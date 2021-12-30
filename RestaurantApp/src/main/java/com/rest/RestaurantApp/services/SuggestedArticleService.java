package com.rest.RestaurantApp.services;

import java.util.Date;
import java.util.HashSet;
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
import com.rest.RestaurantApp.domain.SuggestedArticle;
import com.rest.RestaurantApp.domain.enums.ArticleType;
import com.rest.RestaurantApp.dto.ArticleDTO;
import com.rest.RestaurantApp.dto.SuggestedArticleDTO;
import com.rest.RestaurantApp.exceptions.NotFoundException;
import com.rest.RestaurantApp.repositories.ArticleRepository;
import com.rest.RestaurantApp.repositories.IngredientRepository;
import com.rest.RestaurantApp.repositories.PriceInfoRepository;
import com.rest.RestaurantApp.repositories.SuggestedArticleRepository;

@Service
@Transactional
public class SuggestedArticleService implements ISuggestedArticleService{

	private ArticleRepository articleRepository;
	
	private SuggestedArticleRepository suggestedArticleRepository;
	
	private IngredientRepository ingredientRepository;
	
	private PriceInfoRepository priceInfoRepository;
	
	@Autowired
	public SuggestedArticleService(ArticleRepository articleRepository, SuggestedArticleRepository suggestedArticleRepository, 
			IngredientRepository ingredientRepository, PriceInfoRepository priceInfoRepository) {
		this.articleRepository = articleRepository;
		this.suggestedArticleRepository = suggestedArticleRepository;
		this.ingredientRepository = ingredientRepository;
		this.priceInfoRepository = priceInfoRepository;
	}
	
	@Override
	public List<SuggestedArticleDTO> getAll() {
		return suggestedArticleRepository.findAll().stream().map(suggestedArticle -> new SuggestedArticleDTO(suggestedArticle)).collect(Collectors.toList());
	}

	@Override
	public SuggestedArticleDTO getOne(int id) {
		Optional<SuggestedArticle> suggestedArticle =  suggestedArticleRepository.findById(id);
		if(suggestedArticle.isEmpty()) {
			throw new NotFoundException("Suggested article with id " + id + " was not found");
		}
		return new SuggestedArticleDTO(suggestedArticle.get());
	}
	
	public SuggestedArticleDTO approve(int id) {
		Optional<SuggestedArticle> suggestedArticle =  suggestedArticleRepository.findById(id);
		if(suggestedArticle.isEmpty()) {
			throw new NotFoundException("Suggested article with id " + id + " was not found");
		}
		SuggestedArticle approvedArticle = suggestedArticle.get();
		approvedArticle.setDeleted(true);
		suggestedArticleRepository.save(approvedArticle);
		Article newArticle = new Article(approvedArticle.getName(), approvedArticle.getDescription(), approvedArticle.getType());
		Article savedArticle = articleRepository.save(newArticle);
		
		Set<Ingredient> ingredients = new HashSet<>();
		for(Ingredient ingredient:approvedArticle.getIngredients()) {
			ingredient.getArticles().add(savedArticle);
			ingredients.add(ingredient);
		}
		savedArticle.setIngredients(ingredients);
		
		articleRepository.save(savedArticle);
		PriceInfo price = new PriceInfo(new Date(), approvedArticle.getSuggestedMakingPrice(), approvedArticle.getSuggestedSellingPrice(), savedArticle);
		priceInfoRepository.save(price);
		return new SuggestedArticleDTO(suggestedArticle.get());
	}
	
	@Override
	public SuggestedArticleDTO delete(int id) {
		Optional<SuggestedArticle> suggestedArticleData =  suggestedArticleRepository.findById(id);
		if(suggestedArticleData.isEmpty()) {
			throw new NotFoundException("Suggested article with id " + id + " was not found");
		}
		SuggestedArticle suggestedArticle = suggestedArticleData.get();
		suggestedArticle.setDeleted(true);
		suggestedArticleRepository.save(suggestedArticle);
		return new SuggestedArticleDTO(suggestedArticle);
	}

	@Override
	public SuggestedArticleDTO create(SuggestedArticleDTO suggestedArticle) {
		
		SuggestedArticle newSuggestedArticle = new SuggestedArticle(suggestedArticle.getName(), suggestedArticle.getDescription(), suggestedArticle.getSuggestedMakingPrice(),
				suggestedArticle.getSuggestedSellingPrice(), suggestedArticle.getType());
		SuggestedArticle savedSuggestedArticle = suggestedArticleRepository.save(newSuggestedArticle);
		Set<Ingredient> ingredients = suggestedArticle.getIngredients().stream()
				.map(ingredientDTO -> ingredientRepository.findById(ingredientDTO.getId()).orElse(null)).collect(Collectors.toSet());
		savedSuggestedArticle.setIngredients(ingredients);
		return new SuggestedArticleDTO(suggestedArticleRepository.save(savedSuggestedArticle));
	}

	@Override
	public SuggestedArticleDTO update(int id, SuggestedArticleDTO suggestedArticle) {
		Optional<SuggestedArticle> oldSuggestedArticleData = suggestedArticleRepository.findById(id);
		if(oldSuggestedArticleData.isEmpty()) {
			throw new NotFoundException("Suggested article with id " + id + " was not found");
		}
		SuggestedArticle oldSuggestedArticle = oldSuggestedArticleData.get();
		oldSuggestedArticle.setName(suggestedArticle.getName());
		oldSuggestedArticle.setDescription(suggestedArticle.getDescription());
		oldSuggestedArticle.setSuggestedMakingPrice(suggestedArticle.getSuggestedMakingPrice());
		oldSuggestedArticle.setSuggestedSellingPrice(suggestedArticle.getSuggestedSellingPrice());
		oldSuggestedArticle.setType(suggestedArticle.getType());
		Set<Ingredient> ingredients = suggestedArticle.getIngredients().stream()
				.map(ingredientDTO -> ingredientRepository.findById(ingredientDTO.getId()).orElse(null)).collect(Collectors.toSet());
		oldSuggestedArticle.setIngredients(ingredients);
		SuggestedArticle updatedSuggestedArticle = suggestedArticleRepository.save(oldSuggestedArticle);
		return new SuggestedArticleDTO(updatedSuggestedArticle);
	}

}
