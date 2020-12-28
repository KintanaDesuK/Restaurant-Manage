package com.restaurant.demo.entity;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "Recipes")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Recipe {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long recipeId;


	private Integer quantity;
	

	@ManyToOne(fetch = FetchType.LAZY,optional=false)
	@JoinColumn
	private Dish dish;
	
	
	@ManyToOne(fetch = FetchType.LAZY,optional=false)
	@JoinColumn
	private Ingredient ingredient;

	public Recipe() {
		
	}

	public Recipe(Long recipeId, Integer quantity, Dish dish, Ingredient ingredient) {
		super();
		this.recipeId = recipeId;
		this.quantity = quantity;
		this.dish = dish;
		this.ingredient = ingredient;
	}

	public Recipe(Integer quantity, Dish dish, Ingredient ingredient) {
		super();
		this.quantity = quantity;
		this.dish = dish;
		this.ingredient = ingredient;
	}

	public Long getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(Long recipeId) {
		this.recipeId = recipeId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Dish getDish() {
		return dish;
	}

	public void setDish(Dish dish) {
		this.dish = dish;
	}

	public Ingredient getIngredient() {
		return ingredient;
	}

	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}
	
	
}
