package com.example.carservice.repo;


import com.example.carservice.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface StockRepository extends JpaRepository<Stock,Long> {
    @Query("SELECT s FROM Stock s WHERE s.sparePart.name LIKE %:name%")
    List<Stock> findAllByNameContaining(@Param("name") String name);
}
