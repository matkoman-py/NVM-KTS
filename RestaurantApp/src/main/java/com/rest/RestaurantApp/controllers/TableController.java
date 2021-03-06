package com.rest.RestaurantApp.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/table")
public class TableController {
	
	@PreAuthorize("hasRole('MANAGER') or hasAuthority('ROLE_WAITER')")
    @GetMapping
    public ResponseEntity<Object> getTableLayout() {
        try {
            String table = Files.readString(Paths.get("table.json"));
            return ResponseEntity.ok(table);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
	
	@PreAuthorize("hasRole('MANAGER') or hasAuthority('ROLE_WAITER')")
    @PostMapping
    public ResponseEntity<Object> saveTableLayout(@RequestBody String table) {
        try {
            FileWriter writer = new FileWriter("table.json");
            writer.write(table);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(table);
    }
}
