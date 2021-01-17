package com.restaurant.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.restaurant.demo.entity.*;
@Repository
public interface AreaRepository extends JpaRepository<Area, Integer> {
	Page<Area> findAll(Pageable pageable);
	@Query(value = "Select *  from areas, desks where areas.area_id = desks.area_id  and desks.id = ?1", nativeQuery = true)
	List<Area> findAreaByDesk(Integer id);
}
