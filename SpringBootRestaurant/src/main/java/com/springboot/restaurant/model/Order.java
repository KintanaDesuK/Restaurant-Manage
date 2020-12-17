package com.springboot.restaurant.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@javax.persistence.Table(name = "Orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderId;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	@Column(nullable = false)
	private Double totalPrice;

	@Column(nullable = false)
	private Double onSale;

	@Column(nullable = false)
	private Double paid;

	@ManyToOne
	@JoinColumn(name = "staff")
	private Staff staff;
	
	@ManyToOne
	@JoinColumn(name = "customer")
	private Customer customer;
	
	@ManyToOne
	private Table table;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "orderr")
	private List<OrderDetail> orderDetails;

	public Order() {

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

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Double getOnSale() {
		return onSale;
	}

	public void setOnSale(Double onSale) {
		this.onSale = onSale;
	}

	public Double getPaid() {
		return paid;
	}

	public void setPaid(Double paid) {
		this.paid = paid;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}
	
}
