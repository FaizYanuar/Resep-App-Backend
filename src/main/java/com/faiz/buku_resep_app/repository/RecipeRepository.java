package com.faiz.buku_resep_app.repository;

import com.faiz.buku_resep_app.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
