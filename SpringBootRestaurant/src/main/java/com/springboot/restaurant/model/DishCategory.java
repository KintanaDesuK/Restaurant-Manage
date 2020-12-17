package com.springboot.restaurant.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "DishCategories")
public class DishCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dishCateId;

	@Column(nullable = false)
	private String disCateName;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "dishCategory")
	private List<Dish> dishs;

	public DishCategory() {

	}

	public Integer getDishCateId() {
		return dishCateId;
	}

	public void setDishCateId(Integer dishCateId) {
		this.dishCateId = dishCateId;
	}

	public String getDisCateName() {
		return disCateName;
	}

	public void setDisCateName(String disCateName) {
		this.disCateName = disCateName;
	}

	public List<Dish> getDishs() {
		return dishs;
	}

	public void setDishs(List<Dish> dishs) {
		this.dishs = dishs;
	}

}
