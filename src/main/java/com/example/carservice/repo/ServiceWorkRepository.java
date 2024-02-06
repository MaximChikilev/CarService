package com.example.carservice.repo;

import com.example.carservice.entity.ServiceWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ServiceWorkRepository extends JpaRepository<ServiceWork, Long> {
  @Query("SELECT s FROM ServiceWork s WHERE s.name LIKE %:name%")
  List<ServiceWork> findAllByNameContaining(@Param("name") String name);

  @Query("SELECT s FROM ServiceWork s WHERE s.durationInMinutes = :name")
  List<ServiceWork> findAllByDuration(@Param("name") String name);

  @Query(
      "SELECT DISTINCT sw FROM ServiceWork sw JOIN sw.spareParts sp WHERE sp.name LIKE %:sparePartName%")
  List<ServiceWork> findBySparePartNameContaining(@Param("sparePartName") String sparePartName);

  @Query("SELECT s FROM ServiceWork s WHERE s.name = :name")
  ServiceWork findByName(@Param("name") String name);
}
