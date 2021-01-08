package com.restaurant.demo.entity;


import java.util.List;
import java.util.Set;

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

    @OneToMany(mappedBy="ingredientCategory", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Ingredient> ingredients;
    
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

	public Set<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(Set<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public IngredientCategory(String ingCateName, Set<Ingredient> ingredients) {
		super();
		this.ingCateName = ingCateName;
		this.ingredients = ingredients;
	}

	public IngredientCategory() {
    	
    }
	
}
