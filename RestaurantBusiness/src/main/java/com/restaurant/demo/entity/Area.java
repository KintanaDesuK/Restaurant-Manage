package com.restaurant.demo.entity;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "Areas")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Area {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer areaId;
	
	private String areaName;	
	
	
	@OneToMany(mappedBy="area", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Desk> desks;
	
	public Area() {
		
	}

	public Area(String areaName, Set<Desk> desks) {
		super();
		this.areaName = areaName;
		this.desks = desks;
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Set<Desk> getDesks() {
		return desks;
	}

	public void setDesks(Set<Desk> desks) {
		this.desks = desks;
	}

	
}
