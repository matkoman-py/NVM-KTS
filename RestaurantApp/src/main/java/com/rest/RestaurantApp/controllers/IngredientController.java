package com.rest.RestaurantApp.controllers;

import com.rest.RestaurantApp.dto.IngredientDTO;
import com.rest.RestaurantApp.services.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingredient")
public class IngredientController {
    private IngredientService ingredientService;

    @Autowired
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<IngredientDTO>> getAll() { return ResponseEntity.ok(ingredientService.getAll()); }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IngredientDTO> getOne(@PathVariable("id") int id) {
        IngredientDTO ingredient = ingredientService.getOne(id);
        if(ingredient == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ingredient, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IngredientDTO> create(@RequestBody IngredientDTO ingredientDTO) {
        return new ResponseEntity<>(ingredientService.create(ingredientDTO), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<IngredientDTO> update(@PathVariable("id") int id, @RequestBody IngredientDTO ingredientDTO) {
        IngredientDTO updateIngredient = ingredientService.update(id, ingredientDTO);
        if(updateIngredient == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updateIngredient, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delete(@PathVariable("id") int id) {
        IngredientDTO ingredientDTO = ingredientService.delete(id);
        if(ingredientDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Ingredient with id " + id + " successfully deleted", HttpStatus.OK);
    }
}
