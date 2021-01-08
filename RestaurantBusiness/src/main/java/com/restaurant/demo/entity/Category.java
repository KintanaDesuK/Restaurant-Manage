package com.restaurant.demo.entity;

import java.util.Date;
import java.util.List;
import java.util.Set;

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
    
    
    @OneToMany(mappedBy="category", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Dish> dishs;

    public Category() {
    	
    }

	public Category(String name, Set<Dish> dishs) {
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

	public Set<Dish> getDishs() {
		return dishs;
	}

	public void setDishs(Set<Dish> dishs) {
		this.dishs = dishs;
	}

	
    
}
