package com.example.carservice.dao;

import com.example.carservice.entity.ScheduledTechnicalInspections;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduledTechnicalInspectionsRepository extends JpaRepository<ScheduledTechnicalInspections,Long> {
}
