package com.example.carservice.dao;

import com.example.carservice.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CarRepository extends JpaRepository<Car,Long> {
}
