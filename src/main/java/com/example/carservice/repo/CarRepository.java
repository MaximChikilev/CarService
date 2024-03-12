package com.example.carservice.repo;

import com.example.carservice.dto.ConnectionsWithOtherEntityDTO;
import com.example.carservice.dto.InspectionDTO;
import com.example.carservice.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Date;
import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
  @Query("SELECT s FROM Car s WHERE s.licensePlateNumber LIKE %:name%")
  List<Car> findAllByLicensePlateNumberContaining(@Param("name") String name);

  @Query("SELECT s FROM Car s WHERE s.manufactureYear = :name")
  List<Car> findAllByManufacturerYear(@Param("name") String name);

  @Query("SELECT s FROM Car s WHERE s.model LIKE %:name%")
  List<Car> findAllByModelContaining(@Param("name") String name);

  @Query("SELECT s FROM Car s WHERE s.vinCode LIKE %:name%")
  List<Car> findAllByVinCodeContaining(@Param("name") String name);

  @Query("SELECT s FROM Car s WHERE s.manufacturer.name LIKE %:name%")
  List<Car> findAllByManufacturerNameContaining(@Param("name") String name);

  @Query("SELECT s FROM Car s WHERE s.licensePlateNumber = :name")
  Car findByLicensePlateNumber(@Param("name") String name);

  @Query(
      "SELECT COUNT(ms) FROM MaintenanceSchedule ms "
          + "WHERE ms.maintenanceDate >= :startDate "
          + "AND ms.technicalInspection.name = :technicalInspectionName "
          + "AND ms.car.licensePlateNumber = :licensePlateNumber")
  int countByDateAndTechnicalInspectionAndCar(
      @Param("startDate") Date startDate,
      @Param("technicalInspectionName") String technicalInspectionName,
      @Param("licensePlateNumber") String licensePlateNumber);

  @Query("SELECT NEW com.example.carservice.dto.ConnectionsWithOtherEntityDTO('Client', COUNT(c.clientId)) " +
          "FROM Client c WHERE c.car.carId=:carId ")
  List<ConnectionsWithOtherEntityDTO> getConnectionWithClients(@Param("carId") Long carId);
  @Query("SELECT NEW com.example.carservice.dto.ConnectionsWithOtherEntityDTO('Schedule', COUNT(c.maintenanceScheduleId)) " +
          "FROM MaintenanceSchedule c WHERE c.car.carId=:carId ")
  List<ConnectionsWithOtherEntityDTO> getConnectionWithSchedule(@Param("carId") Long carId);
}
