package com.restaurant.demo.repository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.restaurant.demo.entity.*;
@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {

//	@Query("select d from dishs d, categories c where d.category_id = ?1 and d.category_id = c.id")
	Page<Dish> findByCategoryId(int category_id, Pageable pageAble);
	
}
