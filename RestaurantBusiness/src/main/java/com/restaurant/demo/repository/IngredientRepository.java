package com.restaurant.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restaurant.demo.entity.*;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

	
}
