package com.rest.RestaurantApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.rest.RestaurantApp.dto.MenuDTO;
import com.rest.RestaurantApp.exceptions.NotFoundException;
import com.rest.RestaurantApp.services.MenuService;

@RestController
@RequestMapping("/api/menu")
public class MenuController {
	private MenuService menuService;

	@Autowired
	public MenuController(MenuService menuService) {
		this.menuService = menuService;
	}
	
	@ExceptionHandler(value = NotFoundException.class)
	public ResponseEntity handleNullArticlesException(NotFoundException notFoundException) {
        return new ResponseEntity(notFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }
	
	@PreAuthorize("hasRole('MANAGER')")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MenuDTO> getMenu() {
		return ResponseEntity.ok(menuService.getMenu(420));
	}
	
	@PreAuthorize("hasRole('MANAGER')")
	@PutMapping(path = "delete-article/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MenuDTO> deleteArticle(@PathVariable("id") int id) {
		return ResponseEntity.ok(menuService.deleteArticle(420,id));
	}
	@PreAuthorize("hasRole('MANAGER')")
	@PutMapping(path = "add-article/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MenuDTO> addArticle(@PathVariable("id") int id) {
		return ResponseEntity.ok(menuService.addArticle(420,id));
	}
}
