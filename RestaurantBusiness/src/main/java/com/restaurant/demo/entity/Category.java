package com.restaurant.demo.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "Categories")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = true, length = 150)
    private String name;
    
    
    @JsonBackReference
    @OneToMany(mappedBy="category", cascade=CascadeType.ALL)
    private List<Dish> dishs;

    public Category() {
    	
    }

	public Category(Integer id, String name, List<Dish> dishs) {
		super();
		this.id = id;
		this.name = name;
		this.dishs = dishs;
	}

	public Category(String name, List<Dish> dishs) {
		super();
		this.name = name;
		this.dishs = dishs;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Dish> getDishs() {
		return dishs;
	}

	public void setDishs(List<Dish> dishs) {
		this.dishs = dishs;
	}
    
    
}
