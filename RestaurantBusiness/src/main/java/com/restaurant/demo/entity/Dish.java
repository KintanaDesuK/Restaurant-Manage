package com.restaurant.demo.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@Table(name = "Dishs")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Dish {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long dishId;

	
	private String dishName;

	
	private Double dishPrice;

	
	private Double onSale;

	private String imagePath;

	@Enumerated(EnumType.ORDINAL)
	private Status status;
	
	
    @ManyToOne(fetch = FetchType.LAZY,optional=false)
    @JoinColumn
    private Category category;
    
    
	@OneToMany(mappedBy = "dish", cascade=CascadeType.ALL)
	private List<Recipe> recipes;

    public Dish() {
    	
    }

	public Dish(Long dishId, String dishName, Double dishPrice, Double onSale, String imagePath, Status status,
			Category category, List<Recipe> recipes) {
		super();
		this.dishId = dishId;
		this.dishName = dishName;
		this.dishPrice = dishPrice;
		this.onSale = onSale;
		this.imagePath = imagePath;
		this.status = status;
		this.category = category;
		this.recipes = recipes;
	}

	public List<Recipe> getRecipes() {
		return recipes;
	}

	public void setRecipes(List<Recipe> recipes) {
		this.recipes = recipes;
	}

	public Dish(Long dishId, String dishName, Double dishPrice, Double onSale, String imagePath, Status status,
			Category category) {
		super();
		this.dishId = dishId;
		this.dishName = dishName;
		this.dishPrice = dishPrice;
		this.onSale = onSale;
		this.imagePath = imagePath;
		this.status = status;
		this.category = category;
	}

	public Dish(String dishName, Double dishPrice, Double onSale, String imagePath, Status status, Category category) {
		super();
		this.dishName = dishName;
		this.dishPrice = dishPrice;
		this.onSale = onSale;
		this.imagePath = imagePath;
		this.status = status;
		this.category = category;
	}

	public Long getDishId() {
		return dishId;
	}

	public void setDishId(Long dishId) {
		this.dishId = dishId;
	}

	public String getDishName() {
		return dishName;
	}

	public void setDishName(String dishName) {
		this.dishName = dishName;
	}

	public Double getDishPrice() {
		return dishPrice;
	}

	public void setDishPrice(Double dishPrice) {
		this.dishPrice = dishPrice;
	}

	public Double getOnSale() {
		return onSale;
	}

	public void setOnSale(Double onSale) {
		this.onSale = onSale;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
    
    
}
