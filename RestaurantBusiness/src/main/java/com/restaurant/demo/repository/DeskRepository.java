package com.restaurant.demo.repository;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restaurant.demo.entity.*;
@Repository
public interface DeskRepository extends JpaRepository<Desk, Integer> {
	Page<Desk> findByAreaAreaId( int areaId, Pageable pageable);
	Page<Desk> findAll(Pageable pageable);
}
