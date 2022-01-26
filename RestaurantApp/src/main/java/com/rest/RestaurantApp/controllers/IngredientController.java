package com.rest.RestaurantApp.controllers;

import com.rest.RestaurantApp.domain.Role;
import com.rest.RestaurantApp.dto.IngredientDTO;
import com.rest.RestaurantApp.exceptions.NotFoundException;
import com.rest.RestaurantApp.services.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/ingredient")
public class IngredientController {

    private IngredientService ingredientService;

    @Autowired
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity handleNullArticlesException(NotFoundException notFoundException) {
        return new ResponseEntity(notFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<List<IngredientDTO>> getAll() {
        return ResponseEntity.ok(ingredientService.getAll()); }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IngredientDTO> getOne(@PathVariable("id") int id) {
        IngredientDTO ingredient = ingredientService.getOne(id);
        return new ResponseEntity<>(ingredient, HttpStatus.OK);
    }
    
    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<IngredientDTO>> search(@RequestParam(value = "type", required = false, defaultValue = "") String type,
			@RequestParam(value = "name", required = false, defaultValue = "") String name) {
        return new ResponseEntity<>(ingredientService.search(name, type), HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IngredientDTO> create(@RequestBody IngredientDTO ingredientDTO) {
        return new ResponseEntity<>(ingredientService.create(ingredientDTO), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IngredientDTO> update(@PathVariable("id") int id, @RequestBody IngredientDTO ingredientDTO) {
        IngredientDTO updateIngredient = ingredientService.update(id, ingredientDTO);
        return new ResponseEntity<>(updateIngredient, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces="text/plain")
    public ResponseEntity<String> delete(@PathVariable("id") int id) {
        IngredientDTO ingredientDTO = ingredientService.delete(id);
        return new ResponseEntity<>("Ingredient with id " + id + " successfully deleted", HttpStatus.OK);
    }
}
