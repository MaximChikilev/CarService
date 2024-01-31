package com.example.carservice.repo;

import com.example.carservice.entity.SparePart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SparePartRepository extends JpaRepository<SparePart,Long> {
    @Query("SELECT s FROM SparePart s WHERE s.name LIKE %:name%")
    List<SparePart> findAllByNameContaining(@Param("name") String name);
    @Query("SELECT s FROM SparePart s WHERE s.partNumber LIKE %:name%")
    List<SparePart> findAllByPartNumberContaining(@Param("name") String name);
    @Query("SELECT s FROM SparePart s WHERE s.partNumber = :name")
    SparePart findByPartNumber(@Param("name") String name);
}
