package com.example.carservice.dao;

import com.example.carservice.entity.MaintenanceSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaintenanceScheduleRepository extends JpaRepository<MaintenanceSchedule,Long> {
}
