package com.springboot.restaurant.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "IngredientCategories")
public class IngredientCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer ingCateId;
	
	@Column(nullable = false)
	private String ingCateName;

	public IngredientCategory() {

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

}
