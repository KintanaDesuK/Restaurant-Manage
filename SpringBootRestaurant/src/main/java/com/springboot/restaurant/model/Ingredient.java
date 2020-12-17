package com.springboot.restaurant.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Ingredients")
public class Ingredient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ingId;

	@Column(unique = true, nullable = false)
	private String ingName;

	@Column(nullable = false)
	private Double ingPrice;

	@Column(nullable = false)
	private String unit;

	@Column(nullable = false)
	private Integer quantity;

	@ManyToOne
	private IngredientCategory ingredientCategory;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "ingredient")
	private List<Recipe> recipes;

	public Ingredient() {

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

}
