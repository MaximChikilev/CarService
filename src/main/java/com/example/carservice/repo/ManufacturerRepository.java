package com.example.carservice.repo;

import com.example.carservice.entity.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ManufacturerRepository extends JpaRepository<Manufacturer,Long> {
    @Query("SELECT s FROM Manufacturer s WHERE s.name LIKE %:name%")
    List<Manufacturer> findAllByNameContaining(@Param("name") String name);
    @Query("SELECT s FROM Manufacturer s WHERE s.name = :name")
    Manufacturer findByName(@Param("name") String name);
}
