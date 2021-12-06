package com.rest.RestaurantApp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rest.RestaurantApp.dto.SuggestedArticleDTO;
import com.rest.RestaurantApp.exceptions.NotFoundException;
import com.rest.RestaurantApp.services.SuggestedArticleService;

@RestController
@RequestMapping("/api/suggestedArticle")
public class SuggestedArticleController {
	
	private SuggestedArticleService suggestedArticleService;

	@Autowired
	public SuggestedArticleController(SuggestedArticleService suggestedArticleService) {
		this.suggestedArticleService = suggestedArticleService;
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<SuggestedArticleDTO>> getAll() {
		return ResponseEntity.ok(suggestedArticleService.getAll());
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SuggestedArticleDTO> getOne(@PathVariable("id") int id) {
		SuggestedArticleDTO suggestedArticle = suggestedArticleService.getOne(id);
		/*
		if(suggestedArticle == null) {
			return new ResponseEntity<SuggestedArticleDTO>(HttpStatus.NOT_FOUND);
		}
		*/
		return new ResponseEntity<SuggestedArticleDTO>(suggestedArticle, HttpStatus.OK);	
	}
	////
	@GetMapping(value = "/approve/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SuggestedArticleDTO> approve(@PathVariable("id") int id) {
		SuggestedArticleDTO suggestedArticle = suggestedArticleService.approve(id);
		/*
		if(suggestedArticle == null) {
			return new ResponseEntity<SuggestedArticleDTO>(HttpStatus.NOT_FOUND);
		}
		*/
		return new ResponseEntity<SuggestedArticleDTO>(suggestedArticle, HttpStatus.OK);	
	}
	////
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity delete(@PathVariable("id") int id) {
		SuggestedArticleDTO suggestedArticle = suggestedArticleService.delete(id);
		/*
		if(suggestedArticle == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		*/
		return new ResponseEntity<>("Suggested article with id " + id + " successfully deleted", HttpStatus.OK);	
	}
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SuggestedArticleDTO> create(@RequestBody SuggestedArticleDTO suggestedArticle) {
		SuggestedArticleDTO createdSuggestedArticle = suggestedArticleService.create(suggestedArticle);
		return new ResponseEntity<SuggestedArticleDTO>(createdSuggestedArticle, HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SuggestedArticleDTO> update(@PathVariable("id") int id, @RequestBody SuggestedArticleDTO suggestedArticle) {
		SuggestedArticleDTO updateSuggestedArticle = suggestedArticleService.update(id, suggestedArticle);
		/*
		if(updateSuggestedArticle == null) {
			return new ResponseEntity<SuggestedArticleDTO>(HttpStatus.NOT_FOUND);
		}
		*/
		return new ResponseEntity<SuggestedArticleDTO>(updateSuggestedArticle, HttpStatus.OK);
	}
	
	@ExceptionHandler(value = NotFoundException.class)
	public ResponseEntity handleNullArticlesException(NotFoundException notFoundException) {
        return new ResponseEntity(notFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }
}
