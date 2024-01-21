package com.example.carservice.repo;

import com.example.carservice.entity.TechnicalInspection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TechnicalInspectionsRepository extends JpaRepository<TechnicalInspection,Long> {
    @Query("SELECT s FROM TechnicalInspection s WHERE s.name LIKE %:name%")
    List<TechnicalInspection> findAllByNameContaining(@Param("name") String name);
}
