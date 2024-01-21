package com.example.carservice.repo;

import com.example.carservice.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client,Long> {
    @Query("SELECT s FROM Client s WHERE s.firstName LIKE %:name%")
    List<Client> findAllByFirstNameContaining(@Param("name") String name);
    @Query("SELECT s FROM Client s WHERE s.secondName LIKE %:name%")
    List<Client> findAllBySecondNameContaining(@Param("name") String name);
    @Query("SELECT s FROM Client s WHERE s.phoneNumber LIKE %:name%")
    List<Client> findAllByPhoneNumberContaining(@Param("name") String name);
    @Query("SELECT s FROM Client s WHERE s.address LIKE %:name%")
    List<Client> findAllByAddressContaining(@Param("name") String name);
}
