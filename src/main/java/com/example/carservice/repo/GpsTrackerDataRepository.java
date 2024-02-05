package com.example.carservice.repo;

import com.example.carservice.entity.Car;
import com.example.carservice.entity.GpsTrackerData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GpsTrackerDataRepository extends JpaRepository<GpsTrackerData,Long> {
    @Query("SELECT s FROM GpsTrackerData s WHERE s.car.licensePlateNumber LIKE %:name%")
    List<GpsTrackerData> findAllByCarLicensePlateNumberContaining(@Param("name") String name);
}
