package com.example.carservice.repo;

import com.example.carservice.dto.ClientCarAVGMileageDTO;
import com.example.carservice.dto.ConnectionsWithOtherEntityDTO;
import com.example.carservice.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {
  @Query("SELECT s FROM Client s WHERE s.firstName LIKE %:name%")
  List<Client> findAllByFirstNameContaining(@Param("name") String name);

  @Query("SELECT s FROM Client s WHERE s.secondName LIKE %:name%")
  List<Client> findAllBySecondNameContaining(@Param("name") String name);

  @Query("SELECT s FROM Client s WHERE s.phoneNumber LIKE %:name%")
  List<Client> findAllByPhoneNumberContaining(@Param("name") String name);

  @Query("SELECT s FROM Client s WHERE s.email LIKE %:name%")
  List<Client> findAllByEmailContaining(@Param("name") String name);

  @Query("SELECT s FROM Client s WHERE s.email = :name")
  Client findByEmail(@Param("name") String name);
  @Query("SELECT s FROM Client s WHERE s.phoneNumber = :name")
  Client findByPhoneNumber(@Param("name") String name);

  @Query(
      "SELECT NEW com.example.carservice.dto.ClientCarAVGMileageDTO (c.firstName,c.secondName, c.phoneNumber,c.email, g.car.licensePlateNumber,g.car.mileage,AVG(g.mileage)) "
          + "FROM Client c LEFT JOIN GpsTrackerData g ON c.car = g.car GROUP BY c.firstName, c.secondName, c.phoneNumber, c.email, g.car.licensePlateNumber,g.car.mileage")
  List<ClientCarAVGMileageDTO> getClientCarAverageMileage();

  @Query("SELECT c.car.licensePlateNumber FROM Client c WHERE c.email=:name ")
  List<String> getLicensePlateNumbersByEmail(@Param("name") String name);

  @Query("SELECT NEW com.example.carservice.dto.ConnectionsWithOtherEntityDTO('Schedule', COUNT(c.maintenanceScheduleId)) " +
          "FROM MaintenanceSchedule c WHERE c.client.clientId=:id ")
  List<ConnectionsWithOtherEntityDTO> getConnectionWithMaintenanceSchedule(@Param("id") Long id);
}
