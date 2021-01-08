package com.restaurant.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurant.demo.entity.Order;
import com.restaurant.demo.entity.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
	Page<OrderDetail> findAll(Pageable pageable);
}
