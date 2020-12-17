package com.springboot.restaurant.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Dish {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long dishId;

	@Column(unique = true, nullable = false)
	private String dishName;

	@Column(nullable = false)
	private Double dishPrice;

	@Column(nullable = false)
	private Double onSale;

	@Column(length = 5000, nullable = false)
	private byte[] avatar;

	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = false)
	private Status status;

	@ManyToOne
	@JoinColumn(name = "dishCategory")
	private DishCategory dishCategory;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "dish")
	private List<OrderDetail> orderDetails;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "dish")
	private List<Recipe> recipes;

	public Dish() {

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

	public byte[] getAvatar() {
		return avatar;
	}

	public void setAvatar(byte[] avatar) {
		this.avatar = avatar;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public DishCategory getDishCategory() {
		return dishCategory;
	}

	public void setDishCategory(DishCategory dishCategory) {
		this.dishCategory = dishCategory;
	}

	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public List<Recipe> getRecipes() {
		return recipes;
	}

	public void setRecipes(List<Recipe> recipes) {
		this.recipes = recipes;
	}

}
