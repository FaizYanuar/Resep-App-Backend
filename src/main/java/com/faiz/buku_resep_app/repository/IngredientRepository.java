package com.faiz.buku_resep_app.repository;

import com.faiz.buku_resep_app.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}
