package com.example.carservice.dao;

import com.example.carservice.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StockRepository extends JpaRepository<Stock,Long> {
}
