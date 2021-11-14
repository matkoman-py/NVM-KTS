package com.rest.RestaurantApp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rest.RestaurantApp.dto.ArticleDTO;
import com.rest.RestaurantApp.services.ArticleService;

@RestController
@RequestMapping("/api/article")
public class ArticleController {
	
	private ArticleService articleService;

	@Autowired
	public ArticleController(ArticleService articleService) {
		this.articleService = articleService;
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ArticleDTO>> getAll() {
		return ResponseEntity.ok(articleService.getAll());
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArticleDTO> getOne(@PathVariable("id") int id) {
		ArticleDTO article = articleService.getOne(id);
		if(article == null) {
			return new ResponseEntity<ArticleDTO>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ArticleDTO>(article, HttpStatus.OK);	
	}
	
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity delete(@PathVariable("id") int id) {
		ArticleDTO article = articleService.delete(id);
		if(article == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("Article with id " + id + " successfully deleted", HttpStatus.OK);	
	}
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArticleDTO> create(@RequestBody ArticleDTO article) {
		ArticleDTO createdArticle = articleService.create(article);
		return new ResponseEntity<ArticleDTO>(createdArticle, HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArticleDTO> update(@PathVariable("id") int id, @RequestBody ArticleDTO article) {
		ArticleDTO updateArticle = articleService.update(id, article);
		if(updateArticle == null) {
			return new ResponseEntity<ArticleDTO>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ArticleDTO>(updateArticle, HttpStatus.OK);
	}
}
