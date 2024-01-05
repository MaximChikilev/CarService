package com.example.carservice.dao;

import com.example.carservice.entity.ServiceWork;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceWorkRepository extends JpaRepository<ServiceWork,Long> {
}
