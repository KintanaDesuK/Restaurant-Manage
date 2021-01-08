package com.restaurant.demo.entity;

import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "Ingredients")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Ingredient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ingId;

	@Column(unique = true)
	private String ingName;

	private Double ingPrice;


	private String unit;


	private Integer quantity;

	@ManyToOne(fetch = FetchType.LAZY,optional=false)
	@JoinColumn(name = "ingredientCategory_id", nullable = false)
    @JsonIgnore
	private IngredientCategory ingredientCategory;
	
	@OneToMany(mappedBy = "ingredient", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Recipe> recipes;
	
	public Ingredient() {
		
	}

	public Ingredient(String ingName, Double ingPrice, String unit, Integer quantity,
			IngredientCategory ingredientCategory, Set<Recipe> recipes) {
		super();
		this.ingName = ingName;
		this.ingPrice = ingPrice;
		this.unit = unit;
		this.quantity = quantity;
		this.ingredientCategory = ingredientCategory;
		this.recipes = recipes;
	}

	public Long getIngId() {
		return ingId;
	}

	public void setIngId(Long ingId) {
		this.ingId = ingId;
	}

	public String getIngName() {
		return ingName;
	}

	public void setIngName(String ingName) {
		this.ingName = ingName;
	}

	public Double getIngPrice() {
		return ingPrice;
	}

	public void setIngPrice(Double ingPrice) {
		this.ingPrice = ingPrice;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public IngredientCategory getIngredientCategory() {
		return ingredientCategory;
	}

	public void setIngredientCategory(IngredientCategory ingredientCategory) {
		this.ingredientCategory = ingredientCategory;
	}

	public Set<Recipe> getRecipes() {
		return recipes;
	}

	public void setRecipes(Set<Recipe> recipes) {
		this.recipes = recipes;
	}

	
	
}
