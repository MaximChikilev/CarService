package com.example.carservice.dao;

import com.example.carservice.entity.SparePart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SparePartRepository extends JpaRepository<SparePart,Long> {
}
