package com.restaurant.demo.entity;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "Orders")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderId;

	@Column(updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate = new Date();
	
	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	private String customerName;

	private Double totalPrice;
	
	private Double promotion;
	
	private Double moneyPaid;
	
	@Enumerated(EnumType.ORDINAL)
    private OrderStatus status;
	
	@ManyToOne(fetch = FetchType.LAZY,optional=false)
	@JoinColumn(name = "desk_id", nullable = false)
	@JsonIgnore
	private Desk desk;
	
	@ManyToOne(fetch = FetchType.LAZY,optional=false)
	@JoinColumn(name = "user_id", nullable = false)
	@JsonIgnore
	private User user;
	
	@OneToMany(mappedBy="order", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<OrderDetail> orderDetails;
	
	public Order() {
		
	}



	public Order(Date createDate, String customerName, Double totalPrice, Double promotion, Double moneyPaid,
			OrderStatus status, Desk desk, User user, Set<OrderDetail> orderDetails) {
		super();
		this.createDate = createDate;
		this.customerName = customerName;
		this.totalPrice = totalPrice;
		this.promotion = promotion;
		this.moneyPaid = moneyPaid;
		this.status = status;
		this.desk = desk;
		this.user = user;
		this.orderDetails = orderDetails;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Double getPromotion() {
		return promotion;
	}

	public void setPromotion(Double promotion) {
		this.promotion = promotion;
	}

	public Double getMoneyPaid() {
		return moneyPaid;
	}

	public void setMoneyPaid(Double moneyPaid) {
		this.moneyPaid = moneyPaid;
	}

	public Desk getDesk() {
		return desk;
	}

	public void setDesk(Desk desk) {
		this.desk = desk;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(Set<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}
	
}
