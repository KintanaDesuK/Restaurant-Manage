package com.restaurant.demo.entity;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;


@Entity
@Table(name = "Users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	
	private String fullName;
	
	private String username;
	
	private String password;
	private String email;

	private String address;

	private String phoneNum;

	
	
	@Enumerated(EnumType.ORDINAL)
	private Status status;
	private Gender gender;
	
	
	@ManyToMany(fetch = FetchType.LAZY) 
	@JoinTable(	name = "user_roles", 
				joinColumns = @JoinColumn(name = "user_id"), 
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;
    
	@OneToMany(mappedBy="user", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Order> orders;
	
	
	public User() {
		
	}



	public Set<Order> getOrders() {
		return orders;
	}



	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}



	public String getPhoneNum() {
		return phoneNum;
	}




	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}



	public Status getStatus() {
		return status;
	}


	public void setStatus(Status status) {
		this.status = status;
	}



	public Gender getGender() {
		return gender;
	}



	public void setGender(Gender gender) {
		this.gender = gender;
	}


	public Set<Role> getRoles() {
		return roles;
	}



	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}







	public User(String fullName, String username, String password, String email, String address, String phoneNum,
			Status status, Gender gender, Set<Role> roles, Set<Order> orders) {
		super();
		this.fullName = fullName;
		this.username = username;
		this.password = password;
		this.email = email;
		this.address = address;
		this.phoneNum = phoneNum;
		this.status = status;
		this.gender = gender;
		this.roles = roles;
		this.orders = orders;
	}



	public User(String username,  String email, String password) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		
	}



	public User(String fullName, String username, String password, String email, String address, String phoneNum,
			Status status, Gender gender, Set<Role> roles) {
		super();
		this.fullName = fullName;
		this.username = username;
		this.password = password;
		this.email = email;
		this.address = address;
		this.phoneNum = phoneNum;
		this.status = status;
		this.gender = gender;
		this.roles = roles;
	}



	public User(Long userId, String fullName, String username, String password, String email, String address,
			String phoneNum, Status status, Gender gender, Set<Role> roles) {
		super();
		this.userId = userId;
		this.fullName = fullName;
		this.username = username;
		this.password = password;
		this.email = email;
		this.address = address;
		this.phoneNum = phoneNum;
		this.status = status;
		this.gender = gender;
		this.roles = roles;
	}




	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	
	
}
