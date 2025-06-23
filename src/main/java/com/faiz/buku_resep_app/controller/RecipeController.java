// src/main/java/com/faiz/buku_resep_app/controller/RecipeController.java
package com.faiz.buku_resep_app.controller;

import com.faiz.buku_resep_app.model.Recipe;
import com.faiz.buku_resep_app.model.User;
import com.faiz.buku_resep_app.repository.RecipeRepository;
import com.faiz.buku_resep_app.repository.UserRepository; // <-- IMPORT
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/recipes")
public class RecipeController {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UserRepository userRepository; // <-- INJECT THE USER REPOSITORY

    @PostMapping
    Recipe newRecipe(@RequestBody Recipe newRecipe){
        // --- THE CORRECT IMPLEMENTATION ---
        // 1. Fetch the fully managed User entity from the database.
        User user = userRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Error: User with ID 1 not found in database."));

        // 2. Associate this real user with the incoming recipe.
        newRecipe.setUser(user);

        // 3. Save the recipe. JPA now understands the relationship correctly.
        return recipeRepository.save(newRecipe);
    }

    @GetMapping
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }
}