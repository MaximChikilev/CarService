package com.example.carservice.repo;

import com.example.carservice.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CarRepository extends JpaRepository<Car,Long> {
    @Query("SELECT s FROM Car s WHERE s.licensePlateNumber LIKE %:name%")
    List<Car> findAllByLicensePlateNumberContaining(@Param("name") String name);
    @Query("SELECT s FROM Car s WHERE s.manufactureYear = :name")
    List<Car> findAllByManufacturerYear(@Param("name") String name);
    @Query("SELECT s FROM Car s WHERE s.model LIKE %:name%")
    List<Car> findAllByModelContaining(@Param("name") String name);
    @Query("SELECT s FROM Car s WHERE s.vinCode LIKE %:name%")
    List<Car> findAllByVinCodeContaining(@Param("name") String name);
    @Query("SELECT s FROM Car s WHERE s.manufacturer.name LIKE %:name%")
    List<Car> findAllByManufacturerNameContaining(@Param("name") String name);
}
