package com.restaurant.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.restaurant.demo.entity.*;
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	Page<Order> findAll(Pageable pageable);
	 @Query(value = "SELECT * FROM orders,desks Where desk_id = ?1 and orders.status = 1 and orders.desk_id = desks.id", nativeQuery = true)
	 List<Order> findDeskByDeskId(Integer deskId);
}
