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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Staffs")
public class Staff {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long staffId;

	@Column(nullable = false)
	private String fullName;

	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = false)
	private Gender gender;

	@Column(unique = true, nullable = false)
	private String email;

	private String address;

	@Column(unique = true, length = 20, nullable = false)
	private String phoneNum;

	@Column(length = 5000, nullable = false)
	private byte[] avatar;

	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = false)
	private Status statusStaff;

	@Column(nullable = false)
	private Role role;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "staff")
	private List<Order> orders;

	public Staff() {

	}

	public Long getStaffId() {
		return staffId;
	}

	public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
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

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public byte[] getAvatar() {
		return avatar;
	}

	public void setAvatar(byte[] avatar) {
		this.avatar = avatar;
	}

	public Status getStatusStaff() {
		return statusStaff;
	}

	public void setStatusStaff(Status statusStaff) {
		this.statusStaff = statusStaff;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

}
