package com.example.carservice.repo;

import com.example.carservice.entity.MaintenanceSchedule;
import com.example.carservice.entity.TimeWindows;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Date;
import java.util.List;

public interface MaintenanceScheduleRepository extends JpaRepository<MaintenanceSchedule, Long> {
  @Query("SELECT s FROM MaintenanceSchedule s WHERE s.car.licensePlateNumber LIKE %:value%")
  List<MaintenanceSchedule> findAllByCarLicensePlateNumberContaining(@Param("value") String value);

  @Query("SELECT s FROM MaintenanceSchedule s WHERE s.client.phoneNumber LIKE %:value%")
  List<MaintenanceSchedule> findAllByClientPhoneNumberContaining(@Param("value") String value);

  @Query("SELECT s FROM MaintenanceSchedule s WHERE s.technicalInspection.name LIKE %:value%")
  List<MaintenanceSchedule> findAllByTechnicalInspectionNameContaining(
      @Param("value") String value);

  List<MaintenanceSchedule> findByMaintenanceDateBetween(Date startDate, Date endDate);

  MaintenanceSchedule findByMaintenanceDateAndAndMaintenanceTime(
      Date startDate, TimeWindows timeWindow);

  @Query(
      "SELECT x FROM MaintenanceSchedule x WHERE x.car.licensePlateNumber=:lpn AND x.maintenanceDate>=:date")
  List<MaintenanceSchedule> findByLicensePlateNumberAndDate(
      @Param("lpn") String lpn, @Param("date") Date date);
}
