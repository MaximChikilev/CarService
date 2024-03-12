package com.example.carservice.repo;

import com.example.carservice.dto.ConnectionsWithOtherEntityDTO;
import com.example.carservice.dto.InspectionDTO;
import com.example.carservice.entity.TechnicalInspection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface TechnicalInspectionsRepository extends JpaRepository<TechnicalInspection, Long> {
  @Query("SELECT s FROM TechnicalInspection s WHERE s.name LIKE %:name%")
  List<TechnicalInspection> findAllByNameContaining(@Param("name") String name);

  @Query("SELECT s FROM TechnicalInspection s WHERE s.name = :name")
  TechnicalInspection findByName(@Param("name") String name);

  @Query(
      "SELECT NEW com.example.carservice.dto.InspectionDTO (c.name,c.mileageToPass) FROM TechnicalInspection c ")
  List<InspectionDTO> getInspectionNameWithMileageToPass();

  @Query("SELECT MAX(c.mileageToPass) FROM TechnicalInspection c")
  int getMaxMileageToPass();

  @Query("SELECT NEW com.example.carservice.dto.ConnectionsWithOtherEntityDTO('Schedule', COUNT(c.maintenanceScheduleId)) " +
          "FROM MaintenanceSchedule c WHERE c.technicalInspection.inspectionsId=:id ")
  List<ConnectionsWithOtherEntityDTO> getConnectionWithMaintenanceSchedule(@Param("id") Long id);
}
