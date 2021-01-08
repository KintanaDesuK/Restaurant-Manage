package com.restaurant.demo.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "OrderDetails")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class OrderDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderDetailId;

	private Integer quantity;

	private Double price;
	
	@ManyToOne(fetch = FetchType.LAZY,optional=false)
	@JoinColumn(name = "order_id", nullable = false)
	@JsonIgnore
	private Order order;
	
	
	@ManyToOne(fetch = FetchType.LAZY,optional=false)
	@JoinColumn(name = "dish_id", nullable = false)
	@JsonIgnore
	private Dish dish;
	public OrderDetail() {
		
	}
	
	public Dish getDish() {
		return dish;
	}

	public void setDish(Dish dish) {
		this.dish = dish;
	}

	public OrderDetail(Integer quantity, Double price, Order order, Dish dish) {
		super();
		this.quantity = quantity;
		this.price = price;
		this.order = order;
		this.dish = dish;
	}

	public Long getOrderDetailId() {
		return orderDetailId;
	}
	public void setOrderDetailId(Long orderDetailId) {
		this.orderDetailId = orderDetailId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
}
