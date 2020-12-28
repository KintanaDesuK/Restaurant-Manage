package com.restaurant.demo.entity;


import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@Table(name = "IngredientCategories")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class IngredientCategory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer ingCateId;
	
	private String ingCateName;

    @JsonBackReference
    @OneToMany(mappedBy="ingredientCategory", cascade=CascadeType.ALL)
    private List<Ingredient> ingredients;
    public IngredientCategory() {
    	
    }
	public IngredientCategory(Integer ingCateId, String ingCateName, List<Ingredient> ingredients) {
		super();
		this.ingCateId = ingCateId;
		this.ingCateName = ingCateName;
		this.ingredients = ingredients;
	}
	public IngredientCategory(String ingCateName, List<Ingredient> ingredients) {
		super();
		this.ingCateName = ingCateName;
		this.ingredients = ingredients;
	}
	public Integer getIngCateId() {
		return ingCateId;
	}
	public void setIngCateId(Integer ingCateId) {
		this.ingCateId = ingCateId;
	}
	public String getIngCateName() {
		return ingCateName;
	}
	public void setIngCateName(String ingCateName) {
		this.ingCateName = ingCateName;
	}
	public List<Ingredient> getIngredients() {
		return ingredients;
	}
	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}
    
}
